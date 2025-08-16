package io.eddvance.production.lowcarb.consumption;

public class ConsumptionRequest {
    private String email;
    private Double ratingRequest;

    public ConsumptionRequest() {
    }

    public ConsumptionRequest(String email, Double ratingRequest) {
        this.email = email;
        this.ratingRequest = ratingRequest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getRatingRequest() {
        return ratingRequest;
    }

    public void setRatingRequest(Double ratingRequest) {
        this.ratingRequest = ratingRequest;
    }
}