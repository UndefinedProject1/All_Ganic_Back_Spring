package com.example.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ReportMapper {
    // 신고 날짜 변경하기
    @Update({
        "UPDATE REPORT SET REPORTDATE=#{date} WHERE REPORTCODE=#{no}"
    })
    public void updateReportDate(@Param("date") String date, @Param("no") Long no);

    // 위조금액 적발 횟수 업데이트
    @Update({
        "UPDATE REPORT SET REPORTCOUNT=REPORTCOUNT+1 WHERE REPORTCODE=#{no}"
    })
    public void updateReportCount(@Param("no") Long no);
}
