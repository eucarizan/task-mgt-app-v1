package dev.nj.tms.model;

import java.util.Optional;

public class AppUser {

    private String email;
    private String password;

    public AppUser(String email, String password) {
        setEmail(email);
        setPassword(password);
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
