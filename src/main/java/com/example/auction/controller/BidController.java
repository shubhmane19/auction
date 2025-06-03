package com.example.auction.controller;

import com.example.auction.model.Bid;
import com.example.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping("/place")
    public ResponseEntity<?> placeBid(@RequestBody Bid bid) {
        try {
            bidService.placeBid(bid);
            return ResponseEntity.ok("Bid placed successfully");
        } catch (Exception e) {
            // Consider logging the error for debugging:
            // log.error("Error placing bid: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<Bid>> getBidsForAuction(@PathVariable Long auctionId) {
        return ResponseEntity.ok(bidService.getBidsForAuction(auctionId));
    }
}