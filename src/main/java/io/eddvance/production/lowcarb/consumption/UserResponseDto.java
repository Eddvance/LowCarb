package io.eddvance.production.lowcarb.consumption;

public class UserResponseDto {

    private String email;
    private String ratingRequest;
    private String consummation;
    private String rate;

    public UserResponseDto() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRatingRequest() {
        return ratingRequest;
    }

    public void setRatingRequest(String ratingRequest) {
        this.ratingRequest = ratingRequest;
    }

    public String getConsummation() {
        return consummation;
    }

    public void setConsummation(String format) {
        this.consummation = format;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String format) {
        this.rate = format;
    }
}
