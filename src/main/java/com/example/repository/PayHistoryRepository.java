package com.example.repository;

import java.util.List;

import com.example.entity.Pay;
import com.example.entity.PayHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayHistoryRepository extends JpaRepository<PayHistory, Long>{
    
}
