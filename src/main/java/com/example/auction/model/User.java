package com.example.auction.model;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // Constructors
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public Long getId() { // <-- ADDED THIS GETTER
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(Long id) { // <-- ADDED THIS SETTER
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
        // Corrected: Removed accidental `this.password = password;`
    }

    public void setPassword(String password) { // Added missing setter for password
        this.password = password;
    }
}