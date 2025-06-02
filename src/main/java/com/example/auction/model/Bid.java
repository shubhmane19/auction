package com.example.auction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private LocalDateTime bidTime = LocalDateTime.now();

    @ManyToOne
    private Product product;

    // Constructors
    public Bid() {}

    public Bid(double amount, Product product) {
        this.amount = amount;
        this.product = product;
        this.bidTime = LocalDateTime.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public Product getProduct() {
        return product;
    }

    // Setters
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }
}
