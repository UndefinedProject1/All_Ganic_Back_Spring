package com.example.repository;

import java.util.Optional;

import com.example.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{

    // 회원 장바구니 생성 여부
    Optional<Cart> findByMember_Useremail(String useremail);
}
