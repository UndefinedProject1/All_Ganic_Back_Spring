package com.example.repository;

import java.util.Optional;

import com.example.entity.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
    
    // 접수된 이력이 있는지 확인
    Optional<Report> findByMember_Useremail(String useremail);
}
