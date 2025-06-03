package com.example.auction.repository;

import com.example.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Import this for @Param

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional; // Import Optional for safer handling

public interface BidRepository extends JpaRepository<Bid, Long> {

    // This method already expects auctionId directly, which is fine
    List<Bid> findByAuctionIdOrderByBidTimeDesc(Long auctionId);

    // Using the ID of the Auction entity which is 'id'
    @Query("SELECT MAX(b.amount) FROM Bid b WHERE b.auction.id = :auctionId")
    Optional<BigDecimal> findHighestBidForAuction(@Param("auctionId") Long auctionId);
    // Changed return type to Optional<BigDecimal> for null safety
}