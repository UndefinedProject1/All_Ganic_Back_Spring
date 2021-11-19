package com.example.repository;

import com.example.entity.CancelHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelRepository extends JpaRepository<CancelHistory, Long>{
    
}
