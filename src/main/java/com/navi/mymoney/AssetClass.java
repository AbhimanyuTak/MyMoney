package com.navi.mymoney;

public class AssetClass {

    private float value; 
    private AssetClassType type; 

    public AssetClass(AssetClassType type, float value) {
        this.value = value;
        this.type = type;
    }

    public float getValue() {
        return this.value;
    }

    public AssetClassType getType() {
        return this.type;
    }

    public void setValue(float x) {
        this.value = x;
    }
}
