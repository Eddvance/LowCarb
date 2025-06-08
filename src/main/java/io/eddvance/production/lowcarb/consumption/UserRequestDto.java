package io.eddvance.production.lowcarb.consumption;

public class UserRequestDto {

    private String email;
    private String ratingRequest;

    public UserRequestDto() {}

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
}
