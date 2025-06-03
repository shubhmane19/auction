package com.example.auction.model;

// Removed import com.example.auction.AuctionApplication; // No longer needed
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; // Good practice to include

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bid") // Explicitly name the table
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    private Auction auction; // Changed from AuctionApplication to Auction

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer; // Assuming you have a User entity

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime bidTime;

    // Getters and Setters

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Auction getAuction() { // Return type changed to Auction
        return auction;
    }

    public void setAuction(Auction auction) { // Parameter type changed to Auction
        this.auction = auction;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }
}