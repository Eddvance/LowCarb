package io.eddvance.production.low_carb.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Guest {

    @Id
    private Long id;
    private String email;











    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
