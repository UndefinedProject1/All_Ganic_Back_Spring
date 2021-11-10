package com.example.service;

import com.example.entity.Cart;

import org.springframework.stereotype.Service;

@Service
public interface CartService {

    // 장바구니 추가(생성안되어있을 때)
    public void insertCart(Cart cart);

    // 장바구니 찾기(생성 여부)
    public Cart findCart(String email);
}
