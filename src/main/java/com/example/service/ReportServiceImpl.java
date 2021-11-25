package com.example.service;

import java.util.Optional;

import com.example.entity.Report;
import com.example.mappers.ReportMapper;
import com.example.repository.ReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReportRepository rRepository;

    @Autowired
    ReportMapper rMapper;

    @Override
    public void insertReport(Report report) {
        rRepository.save(report);
    }

    @Override
    public Report findReport(String email) {
        Optional<Report> report = rRepository.findByMember_Useremail(email);
        return report.orElse(null);
    }

    @Override
    public void updateDate(String date, long no) {
        rMapper.updateReportDate(date, no);
    }

    @Override
    public void updateCount(long no) {
        rMapper.updateReportCount(no);        
    }
    
}
