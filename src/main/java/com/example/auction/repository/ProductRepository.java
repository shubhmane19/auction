package com.example.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auction.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}

