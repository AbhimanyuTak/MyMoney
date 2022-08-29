package com.navi.mymoney;

import java.util.HashMap;

public class Portfolio {

    private HashMap<Month, Allocation> monthlyAllocation;   
    private AssetClassType[] types;
    private boolean allocated;
    private float[] initialAllocation;
    private float[] sipAmounts;
    
    public Portfolio(AssetClassType[] types) {
        this.types = types;
        this.allocated = false;
        this.monthlyAllocation = new HashMap<>();
        this.initialAllocation = new float[types.length];
        this.sipAmounts = new float[types.length];
    }

    public void allocate(String[] amountInput) {
        float[] amounts = new float[amountInput.length];
        for (int i = 0; i < amountInput.length; i++) {
            amounts[i] = Float.parseFloat(amountInput[i]);
        }

        this.allocateOnce(amounts);
    }

    // The allocation always happens from January, and SIP from February.
    private void allocateOnce(float[] amounts) {
        if(this.allocated != true) {
            Allocation allocation = this.monthlyAllocation.get(Month.JANUARY);

            if(allocation == null) {
                allocation = new Allocation(types, amounts);
                this.monthlyAllocation.put(Month.JANUARY, allocation);
            }

            for(int i = 0; i < this.types.length; i++) {
                this.initialAllocation[i] = amounts[i];
                allocation.setAmount(this.types[i], amounts[i]);
            }

            this.allocated = true;
        }
        else {
            // Cannot allocate more than once
        }
    }

    /**
     * Formats the input and calls the applyChange method
     * @param monthInput
     * @param changesInput
     */
    public void change(String monthInput, String[] changesInput) {
        float[] changes = new float[this.types.length];
        Month month = Month.valueOf(monthInput);

        for (int i = 0; i < changesInput.length; i++) {
            changes[i] = Float.parseFloat(changesInput[i]);
        }

        this.applyChange(month, changes);
    }

    /**
     * This methods is the gateway to apply monthly sip and then the changes to an allocation
     * @param month
     * @param changes
     */
    private void applyChange(Month month, float[] changes) {
        Allocation currentAllocation = this.monthlyAllocation.get(month);

        if(currentAllocation == null) {
            this.initCurrentMonth(month);
            currentAllocation = this.monthlyAllocation.get(month);
        }

        // First Apply SIP towards the start of month
        // SIP from February
        if(month != Month.JANUARY) {
            this.applySIP(month, currentAllocation, this.sipAmounts);
        }
        // Then apply changes towards the end of month
        this.calculateChange(currentAllocation, changes);
    }

    /**
     * Apply the value of percentage changes to the given allocation
     * @param allocation
     * @param changes
     */
    private void calculateChange(Allocation allocation, float[] changes) {
        for(int i = 0; i < this.types.length; i++) {
            float currentAmount = allocation.getAmount(this.types[i]);
            // Weird multiplication effects
            float amountAfterUpdate = (float)((double)currentAmount * (double)(1 + (double)(changes[i]/100)));
            allocation.setAmount(this.types[i], amountAfterUpdate);
        }
    }

    /**
     * Sets the global SIP amounts for a portfolio
     * @param amountInput
     */
    public void sip(String[] amountInput) {
        float[] sipAmounts = new float[this.types.length];
        for (int i = 0; i < amountInput.length; i++) {
            sipAmounts[i] = Float.parseFloat(amountInput[i]);
        }

        this.setSIP(sipAmounts);
    }

    private void setSIP(float[] amounts) {
        for(int i = 0; i < this.types.length; i++) {
            this.sipAmounts[i] = amounts[i];
        }
    }

    /**
      * This method seeds the current month with the previous month with the exception to January
      * @param month
      */
    private void initCurrentMonth(Month month) {
        int lastMonthIndex = month.ordinal()-1;

        if(lastMonthIndex >= 0) {
            Month lastMonth = Month.values()[month.ordinal()-1];
            Allocation lastMonthAllocation = this.monthlyAllocation.get(lastMonth);
            float[] lastMonthAmounts = lastMonthAllocation.getAllocationAmounts();

            this.monthlyAllocation.put(month, new Allocation(this.types, lastMonthAmounts));
        }
        else {
            // cannot init Jan and before
        }
    }

    private void applySIP(Month month, Allocation allocation, float[] amounts) {
        // System.out.println("Inside applySIP");
        for(int i = 0; i < this.types.length; i++) {
            allocation.addAmount(this.types[i], amounts[i]);
        }
    }

    public int[] balance(String monthInput) {
        Month month = Month.valueOf(monthInput);
        return this.fetchBalanceForCurrentMonth(month);
    }

    // Balances are always floored to the nearest integers.
    private int[] fetchBalanceForCurrentMonth(Month month) {
        Allocation allocation = this.monthlyAllocation.get(month);
        float[] assetClassAmounts = allocation.getAllocationAmounts();
        int[] balances = new int[assetClassAmounts.length];

        for(int i = 0; i < assetClassAmounts.length; i++) {
            balances[i] = (int)assetClassAmounts[i];
        }

        return balances;
    }

    // The rebalancing happens on 6th (June) and 12th (December) month.
    // Balances are always floored to the nearest integers.
    public int[] rebalance() throws Exception {
        Allocation juneAllocation = this.monthlyAllocation.get(Month.JUNE);
        Allocation julyAllocation = this.monthlyAllocation.get(Month.JULY);

        if(juneAllocation != null && julyAllocation == null) {
            juneAllocation.rebalance(this.initialAllocation);
            return this.fetchBalanceForCurrentMonth(Month.JUNE);
        }
        else if(julyAllocation != null && juneAllocation != null){
            Allocation decemberAllocation = this.monthlyAllocation.get(Month.DECEMBER);
            if(decemberAllocation != null) {
                decemberAllocation.rebalance(this.initialAllocation);
                return this.fetchBalanceForCurrentMonth(Month.DECEMBER);
            }
        }
            
        throw new Exception("CANNOT_REBALANCE");        
    }

}
