package io.eddvance.production.lowcarb.pricing.dto;

public class Price {
    String unit;
    Double value;

    public Price() {}

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
