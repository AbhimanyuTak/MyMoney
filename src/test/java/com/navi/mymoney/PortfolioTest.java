package com.navi.mymoney;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class PortfolioTest {
    @Test
    public void shouldAllocate() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        String[] amounts = {"6000", "3000", "1000"};

        Portfolio pf = new Portfolio(types);
        pf.allocate(amounts);

        int[] finalAmounts = {6000, 3000, 1000};

        assertArrayEquals(finalAmounts, pf.balance("JANUARY"));
    } 
    
    @Test
    public void shouldChange() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        String[] amounts = {"6000", "3000", "1000"};
        String[] changesInput = {"10", "10", "10"};

        Portfolio pf = new Portfolio(types);
        pf.allocate(amounts);
        pf.change("JANUARY", changesInput);

        int[] finalAmounts = {6600, 3300, 1100};
        assertArrayEquals(finalAmounts, pf.balance("JANUARY"));
    }
    
    @Test
    public void shouldSetSIP() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        String[] amounts = {"6000", "3000", "1000"};
        String[] sipAmounts = {"1000", "1000", "1000"};
        String[] changesInput = {"0", "0", "0"};

        Portfolio pf = new Portfolio(types);
        pf.allocate(amounts);
        pf.sip(sipAmounts);

        pf.change("JANUARY", changesInput);
        pf.change("FEBRUARY", changesInput);

        int[] finalAmounts = {7000, 4000, 2000};
        assertArrayEquals(finalAmounts, pf.balance("FEBRUARY"));
    } 

    @Test
    public void shouldNotRebalance() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        String[] amounts = {"6000", "3000", "1000"};
        String[] sipAmounts = {"1000", "1000", "1000"};
        String[] changesInput = {"0", "0", "0"};

        Portfolio pf = new Portfolio(types);
        pf.allocate(amounts);
        pf.sip(sipAmounts);

        pf.change("JANUARY", changesInput);
        pf.change("FEBRUARY", changesInput);

        try {
            pf.rebalance();
        } catch(Exception e) {
            assertEquals("CANNOT_REBALANCE", e.getMessage());
        }
    }

    @Test
    public void shouldRebalance() {
        AssetClassType[] types = { AssetClassType.EQUITY, AssetClassType.DEBT, AssetClassType.GOLD};
        String[] amounts = {"6000", "3000", "1000"};
        String[] sipAmounts = {"1000", "1000", "1000"};
        String[] changesInput = {"0", "0", "0"};

        Portfolio pf = new Portfolio(types);
        pf.allocate(amounts);
        pf.sip(sipAmounts);

        pf.change("JANUARY", changesInput);
        pf.change("FEBRUARY", changesInput);
        pf.change("MARCH", changesInput);
        pf.change("APRIL", changesInput);
        pf.change("MAY", changesInput);
        pf.change("JUNE", changesInput);
        
        int[] finalAmounts = {11000, 8000, 6000};

        assertArrayEquals(finalAmounts, pf.balance("JUNE"));

        try {
            pf.rebalance();
        } catch(Exception e) {
        }

        int[] balancedAmounts = {15000, 7500, 2500};

        assertArrayEquals(balancedAmounts, pf.balance("JUNE"));
    }
}
