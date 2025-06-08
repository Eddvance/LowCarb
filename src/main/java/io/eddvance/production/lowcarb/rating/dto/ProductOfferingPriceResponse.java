package io.eddvance.production.lowcarb.rating.dto;

public class ProductOfferingPriceResponse {

    private String id;
    private Price price;
    private UnitOfMeasure unitOfMeasure;

    public ProductOfferingPriceResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}