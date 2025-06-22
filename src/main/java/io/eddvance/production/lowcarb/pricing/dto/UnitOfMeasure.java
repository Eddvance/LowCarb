package io.eddvance.production.lowcarb.pricing.dto;

public class UnitOfMeasure {
    Double amount;
    String units;

    public UnitOfMeasure(){}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
