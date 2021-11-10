package com.example.repository;

import java.util.Optional;

import com.example.entity.Cart;
import com.example.entity.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
