package io.eddvance.production.low_carb.dto.user_request_dto;

public class UserRequestDto {

    private String email;
    private String ratingRequest;

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
