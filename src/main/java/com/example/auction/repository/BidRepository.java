package com.example.auction.repository;

import java.util.List;
import com.example.auction.model.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByProductId(Long productId);
}

