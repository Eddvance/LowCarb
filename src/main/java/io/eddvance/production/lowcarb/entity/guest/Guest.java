package io.eddvance.production.lowcarb.entity.guest;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Guest {

    @Id
    String id;
    String firstName;
    String name;
    String phone;
    String email;

    public Guest() {}
    public Guest(String id, String firstName, String name, String phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
