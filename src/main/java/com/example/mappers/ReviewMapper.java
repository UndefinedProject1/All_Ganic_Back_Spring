package com.example.mappers;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReviewMapper {
    
    // 물품별 리뷰 출력
    @Select({
        "SELECT REVIEWCODE, REVIEWRATING, REVIEWCONTENT, to_char(REVIEWDATE,'YYYY-MM-DD') AS REVIEWDATE ",
        " FROM REVIEW WHERE PRODUCT=#{code}"
    })
    public List<Map<String, Object>> selectProductList(@Param("code") long code);

    // 회원별 리뷰 출력
    @Select({
        "SELECT REVIEWCODE, REVIEWRATING, REVIEWCONTENT, to_char(REVIEWDATE,'YYYY-MM-DD') AS REVIEWDATE ",
        " FROM REVIEW WHERE MEMBER=#{email}"
    })
    public List<Map<String, Object>> selectMemberList(@Param("email") String email);

}
