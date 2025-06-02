package com.example.auction.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auction.repository.BidRepository;
import com.example.auction.repository.ProductRepository;

import com.example.auction.model.*;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> placeBid(@RequestParam Long productId, @RequestParam double bidAmount) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return ResponseEntity.badRequest().body("Product not found");

        Bid bid = new Bid();
        bid.setAmount(bidAmount);
        bid.setProduct(productOpt.get());
        return ResponseEntity.ok(bidRepository.save(bid));
    }

    @GetMapping("/product/{productId}")
    public List<Bid> getBidsForProduct(@PathVariable Long productId) {
        return bidRepository.findByProductId(productId);
    }
}
