package com.example.auction.service;

import com.example.auction.model.Bid;
import com.example.auction.model.Auction; // Import the Auction entity
import com.example.auction.model.User;    // Import the User entity (assuming it exists)
import com.example.auction.repository.BidRepository;
import com.example.auction.repository.AuctionRepository;
import com.example.auction.repository.UserRepository; // Assuming you have a UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    public void placeBid(Bid bid) throws Exception {
        // First, ensure the auction and buyer exist before proceeding
        if (bid.getAuction() == null || bid.getAuction().getId() == null) {
            throw new Exception("Auction information is missing.");
        }

        // --- CORRECTED PART ---
        if (bid.getBuyer() == null || bid.getBuyer().getId() == null) {
            throw new Exception("Buyer information is missing.");
        }
        // --- END CORRECTED PART ---

        // Fetch managed Auction and User entities if they are not already managed
        Optional<Auction> existingAuction = auctionRepository.findById(bid.getAuction().getId());
        if (!existingAuction.isPresent()) {
            throw new Exception("Auction with ID " + bid.getAuction().getId() + " not found.");
        }

        Optional<User> existingBuyer = userRepository.findById(bid.getBuyer().getId());
        if (!existingBuyer.isPresent()) {
            throw new Exception("Buyer with ID " + bid.getBuyer().getId() + " not found.");
        }

        // Set the managed entities on the bid object before saving
        bid.setAuction(existingAuction.get());
        bid.setBuyer(existingBuyer.get()); // Set the managed User entity

        validateBid(bid);
        bid.setBidTime(LocalDateTime.now());
        bidRepository.save(bid);
        notifyOutbidUsers(bid);
    }

    public List<Bid> getBidsForAuction(Long auctionId) {
        return bidRepository.findByAuctionIdOrderByBidTimeDesc(auctionId);
    }

    private void validateBid(Bid bid) throws Exception {
        Optional<BigDecimal> currentHighestOptional = bidRepository.findHighestBidForAuction(bid.getAuction().getId());

        if (currentHighestOptional.isPresent()) {
            BigDecimal currentHighest = currentHighestOptional.get();
            if (bid.getAmount().compareTo(currentHighest) <= 0) {
                throw new Exception("Bid must be higher than the current highest bid.");
            }
        }
    }

    private void notifyOutbidUsers(Bid bid) {
        System.out.println("Notifying outbid users for auction ID: " + bid.getAuction().getId());
    }
}