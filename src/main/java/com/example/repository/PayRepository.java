package com.example.repository;

import java.util.List;

import com.example.entity.Pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay, String>{
    
}
