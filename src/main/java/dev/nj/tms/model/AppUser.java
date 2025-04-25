package dev.nj.tms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Optional;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    public AppUser() {

    }

    public AppUser(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Optional.ofNullable(email)
                .orElseThrow(() -> new IllegalArgumentException("Email cannot be null"));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Optional.ofNullable(password)
                .orElseThrow(() -> new IllegalArgumentException("Password cannot be null"));
    }
}
