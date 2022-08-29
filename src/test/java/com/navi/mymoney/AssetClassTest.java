package com.navi.mymoney;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AssetClassTest {
    
    @Test
    public void shouldSetValue() {
        float value = 100;
        AssetClass assetClass = new AssetClass(AssetClassType.EQUITY, value);
        assertEquals(value, assetClass.getValue());
    }   

    @Test
    public void shouldSetType() {
        float value = 100;
        AssetClassType type = AssetClassType.GOLD;

        AssetClass assetClass = new AssetClass(type, value);
        assertEquals(type, assetClass.getType());
    } 
}
