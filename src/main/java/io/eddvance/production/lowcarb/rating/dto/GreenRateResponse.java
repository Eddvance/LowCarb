package io.eddvance.production.lowcarb.rating.dto;

public class GreenRateResponse {
    private Long id;
    private Double rate;
    private String rateTime;

    public GreenRateResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getRateTime() {
        return rateTime;
    }

    public void setRateTime(String rateTime) {
        this.rateTime = rateTime;
    }
}
