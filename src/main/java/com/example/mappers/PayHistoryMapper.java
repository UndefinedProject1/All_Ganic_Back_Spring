package com.example.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayHistoryMapper {
    
    // 회원과 물품정보에 따른 결제내역 조회(리뷰작성가능한지)
    @Select({
            "SELECT COUNT(MEMBER), REVIEWCHECK FROM PAYHISTORYLIST  ", 
            "WHERE MEMBER=#{email} AND PRODUCTCODE=#{no}",
    })
	public Map<String, Object> selectPayHistoryCheck(@Param("no") Long no, @Param("email") String email);

    // 회원에 따른 주문내역리스트 출력
    @Select({
        "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME, ",
        "ORDERQUANTITY, ORDERDATE, MERCHANT_UID ",
        " FROM PAYHISTORYLIST  WHERE MEMBER=#{email}",
        "ORDER BY ORDERDATE DESC"
    })
    public List<Map<String, Object>> selectPayMemberList(@Param("email") String email);
}
