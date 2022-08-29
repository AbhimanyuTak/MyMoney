package com.navi.mymoney;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class AllocationTest {
    @Test
    public void shouldSetAmounts() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        float[] amounts = {6000, 3000, 1000};

        Allocation allocation = new Allocation(types, amounts);
        assertArrayEquals(amounts, allocation.getAllocationAmounts());
    }   

    @Test
    public void shouldGetAmount() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        float[] amounts = {6000, 3000, 1000};

        Allocation allocation = new Allocation(types, amounts);

        assertEquals(6000, allocation.getAmount(AssetClassType.EQUITY));
    } 

    @Test
    public void shouldAddAmount() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        float[] amounts = {6000, 3000, 1000};

        float amountToBeAdded = 100;
        Allocation allocation = new Allocation(types, amounts);
        allocation.addAmount(AssetClassType.EQUITY, amountToBeAdded);

        float[] updatedAmounts = {6000 + amountToBeAdded, 3000, 1000};
        assertArrayEquals(updatedAmounts, allocation.getAllocationAmounts());
    } 

    @Test
    public void shouldReduceAmount() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        float[] amounts = {6000, 3000, 1000};

        float amountToBeReduced = 100;
        Allocation allocation = new Allocation(types, amounts);
        try {
            allocation.reduceAmount(AssetClassType.EQUITY, amountToBeReduced);
        } catch (Exception e) {

        }

        float[] updatedAmounts = {6000 - amountToBeReduced, 3000, 1000};
        assertArrayEquals(updatedAmounts, allocation.getAllocationAmounts());
    } 

    @Test
    public void shouldRebalance() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        float[] initialAllocation = {6000, 3000, 1000};
        float[] amounts = {10000, 7000, 3000};
        float[] finalAmounts = {12000, 6000, 2000};

        Allocation allocation = new Allocation(types, amounts);
        allocation.rebalance(initialAllocation);

        assertArrayEquals(finalAmounts, allocation.getAllocationAmounts());
    } 
}