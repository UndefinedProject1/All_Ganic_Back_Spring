package com.example.service;

import java.util.Optional;

import com.example.entity.Cart;
import com.example.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cRepository;

    // 장바구니 생성
    @Override
    public void insertCart(Cart cart) {
        cRepository.save(cart);
    }

    // 장바구니 생성 여부확인
    @Override
    public Cart findCart(String email) {
        Optional<Cart> cart = cRepository.findByMember_Useremail(email);
        return cart.orElse(null);
    }
    
}
