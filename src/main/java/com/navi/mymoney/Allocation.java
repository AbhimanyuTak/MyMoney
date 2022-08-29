package com.navi.mymoney;

import java.util.Arrays;

import com.navi.mymoney.AssetClass;

public class Allocation {
    private AssetClass[] assets;
    private float total;
    // private float[] originalAllocationPercentage;

    //  AMOUNT_EQUITY AMOUNT_DEBT AMOUNT_GOLD
    public Allocation(AssetClassType[] types, float[] amounts) {
        // System.out.println("new alloc");
        int assetClassCount = types.length;
        assets = new AssetClass[3];
        this.total = 0;

        for(int i = 0; i < assetClassCount; i++) {
            this.assets[i] = new AssetClass(types[i], amounts[i]);
            // this.total += amounts[i];
        }

        this.updateTotal();
        // this.originalAllocationPercentage = getAllocationPercentage();
    }

    // public void setOriginalAllocationPercentage() {
    //     this.originalAllocationPercentage = this.getAllocationPercentage();
    // }

    // private float[] getAllocationPercentage() {
    //     int totalAssetCount = this.assets.length; 
    //     float[] split = new float[totalAssetCount];

    //     for(int i = 0; i < totalAssetCount; i++) {
    //         System.out.println(this.assets[i].getValue());
    //         System.out.println(this.total);

    //         split[i] = (this.assets[i].getValue()/this.total)*100;

    //         System.out.println(split[i]);
    //     }

    //     return split;
    // }

    private void updateTotal() {
        int totalAssetCount = this.assets.length; 

        this.total = 0;
        for(int i = 0; i < totalAssetCount; i++) {
            this.total += this.assets[i].getValue();
        }
    }

    public void addAmount(AssetClassType type, float amount) {
        int totalAssetCount = this.assets.length; 

        for(int i = 0; i < totalAssetCount; i++) {
            if(this.assets[i].getType() == type) {
                float currentAmount = this.assets[i].getValue();
                this.assets[i].setValue(currentAmount + amount);
            }
        }

        this.updateTotal();
    }

    public void reduceAmount(AssetClassType type, float amount) {
        int totalAssetCount = this.assets.length; 

        for(int i = 0; i < totalAssetCount; i++) {
            if(this.assets[i].getType() == type) {
                float currentAmount = this.assets[i].getValue();

                if(currentAmount - amount >= 0) {
                    this.assets[i].setValue(currentAmount - amount);
                }
                else {
                    // throw error
                }
            }
        }

        this.updateTotal();
    }

    public void setAmount(AssetClassType type, float amount) {
        int totalAssetCount = this.assets.length; 

        for(int i = 0; i < totalAssetCount; i++) {
            if(this.assets[i].getType() == type) {
                this.assets[i].setValue(amount);
            }
        }

        this.updateTotal();
    }

    public float getAmount(AssetClassType type) {
        int totalAssetCount = this.assets.length; 

        float amount = 0;

        for(int i = 0; i < totalAssetCount; i++) {
            if(this.assets[i].getType() == type) {
                amount = this.assets[i].getValue();
            }
        }

        return amount;
    }

    public void rebalance(float[] initialAllocation) {
        int totalAssetCount = this.assets.length; 
        float total = this.total;
        // System.out.println("total");
        // System.out.println(total);

        float totalInitAllocation = 0;
        for (float x : initialAllocation) {
            // System.out.println(x);
            totalInitAllocation += x;
        }

        for(int i = 0; i < totalAssetCount; i++) {
            // float originalAllocation = this.originalAllocationPercentage[i];
            // System.out.println("orig allo");
            // System.out.println(originalAllocation);
            float originalAllocation = (initialAllocation[i] / totalInitAllocation)*100;
            // System.out.println(originalAllocation);
            // System.out.println(total);
            float rebalancedAmount = (originalAllocation * total)/100;
            this.setAmount(this.assets[i].getType(), rebalancedAmount);
        }
    }

    public float[] getAllocationAmounts() {
        // System.out.println("Inside getAllocationAmounts");
        int totalAssetCount = this.assets.length; 
        float[] amounts = new float[totalAssetCount];

        for(int i = 0; i < totalAssetCount; i++) {
            amounts[i] = this.assets[i].getValue();
        }

        return amounts;
    }


}
