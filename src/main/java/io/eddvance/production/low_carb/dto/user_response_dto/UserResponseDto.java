package io.eddvance.production.low_carb.dto.user_response_dto;

public class UserResponseDto {

    private String email;
    private String consummation;
    private String rate;

    public UserResponseDto() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsummation() {
        return consummation;
    }

    public void setConsummation(String consummation) {
        this.consummation = consummation;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
