package io.eddvance.production.low_carb.entity.rating_request;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RatingRequest {

    @Id
    private Long id;
    private String rating;


    public RatingRequest() {}
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
