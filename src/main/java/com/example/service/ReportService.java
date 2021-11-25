package com.example.service;

import com.example.entity.Report;

import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    // 신고 접수
    public void insertReport(Report report);

    // 이미 접수된 사람인지 확인
    public Report findReport(String email);

    // 신고접수된 날짜 업데이트
    public void updateDate(String date, long no);

    // 위조금액 적발 시 count up
    public void updateCount(long no);
}
