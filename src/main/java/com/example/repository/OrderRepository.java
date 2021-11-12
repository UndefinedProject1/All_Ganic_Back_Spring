package com.example.repository;

import java.util.List;

import com.example.entity.Pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Pay, String>{
    
    // 회원별 주문내역
    List<Pay> findByMember_Useremail(String memberemail);
}
