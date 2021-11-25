package com.example.mappers;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReviewMapper {

    // 물품별 리뷰 개수
    @Select({
        "SELECT COUNT(*) FROM REVIEW WHERE PRODUCT=#{code}"
    })
    public int selectProductCNT(@Param("code") Long code);
    
    // 물품별 리뷰 출력
    @Select({
        "SELECT * FROM(SELECT REVIEWCODE, REVIEWRATING, REVIEWCONTENT, REVIEWDATE, USEREMAIL",
        " ROW_NUMBER() OVER (ORDER BY REVIEWDATE DESC) ROWN FROM REVIEWLIST WHERE PRODUCTCODE=#{code})",
        " REVIEWLIST WHERE ROWN BETWEEN #{start} AND #{end}"
    })
    public List<Map<String, Object>> selectProductList(@Param("code") long code, @Param("start") long start, @Param("end") long end);

    // 회원별 리뷰 출력
    @Select({
        "SELECT REVIEWCODE, REVIEWRATING, REVIEWCONTENT, REVIEWDATE ",
        " FROM REVIEWLIST WHERE USEREMAIL=#{email}"
    })
    public List<Map<String, Object>> selectMemberList(@Param("email") String email);

}
