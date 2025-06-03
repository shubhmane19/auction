package com.example.auction.service;

import com.example.auction.model.Bid;
import com.example.auction.model.Auction; // Import the Auction entity
import com.example.auction.repository.BidRepository;
import com.example.auction.repository.AuctionRepository; // Assuming you'll need this to fetch the auction object
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
    private AuctionRepository auctionRepository; // Inject AuctionRepository to fetch Auction object

    public void placeBid(Bid bid) throws Exception {
        // First, ensure the auction and buyer exist before proceeding
        // This is a good practice to prevent detached entity errors or saving bids to non-existent auctions/users.
        if (bid.getAuction() == null || bid.getAuction().getId() == null) {
            throw new Exception("Auction information is missing.");
        }
        if (bid.getBuyer() == null || bid.getBuyer().getId() == null) {
            throw new Exception("Buyer information is missing.");
        }

        // Fetch managed Auction and User entities if they are not already managed
        // This is crucial if the Bid object comes from a DTO or a detached state.
        Optional<Auction> existingAuction = auctionRepository.findById(bid.getAuction().getId());
        if (!existingAuction.isPresent()) {
            throw new Exception("Auction with ID " + bid.getAuction().getId() + " not found.");
        }
        // Assuming you have a UserRepository similar to AuctionRepository
        // Optional<User> existingBuyer = userRepository.findById(bid.getBuyer().getId());
        // if (!existingBuyer.isPresent()) {
        //     throw new Exception("Buyer with ID " + bid.getBuyer().getId() + " not found.");
        // }

        // Set the managed entities on the bid object before saving
        bid.setAuction(existingAuction.get());
        // bid.setBuyer(existingBuyer.get()); // Uncomment if fetching buyer

        validateBid(bid);
        bid.setBidTime(LocalDateTime.now());
        bidRepository.save(bid);
        notifyOutbidUsers(bid);
    }

    public List<Bid> getBidsForAuction(Long auctionId) {
        return bidRepository.findByAuctionIdOrderByBidTimeDesc(auctionId);
    }

    private void validateBid(Bid bid) throws Exception {
        // Pass the auction ID to the repository method
        Optional<BigDecimal> currentHighestOptional = bidRepository.findHighestBidForAuction(bid.getAuction().getId());

        if (currentHighestOptional.isPresent()) {
            BigDecimal currentHighest = currentHighestOptional.get();
            if (bid.getAmount().compareTo(currentHighest) <= 0) {
                throw new Exception("Bid must be higher than the current highest bid.");
            }
        }
        // If currentHighestOptional is empty, it means no bids exist yet for this auction.
        // In a real system, you might want to check against the auction's starting price here.
        // For simplicity, we'll assume the first bid is valid if no prior bids exist.
        // Example: if (bid.getAmount().compareTo(bid.getAuction().getStartingPrice()) < 0) { ... }
    }

    private void notifyOutbidUsers(Bid bid) {
        // Mock notification logic
        System.out.println("Notifying outbid users for auction ID: " + bid.getAuction().getId());
    }
}