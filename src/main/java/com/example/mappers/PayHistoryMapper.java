package com.example.mappers;

import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PayHistoryMapper {
    
    // 회원과 물품정보에 따른 결제내역 조회(리뷰작성가능한지)
    @Select({
            "SELECT COUNT(MEMBER), max(REVIEWCHECK) FROM PAYHISTORYLIST  ", 
            "WHERE MEMBER=#{email} AND PRODUCTCODE=#{no}",
    })
	public Map<String, Object> selectPayHistoryCheck(@Param("no") Long no, @Param("email") String email);

    // 회원에 따른 주문내역리스트 출력
    @Select({
        "SELECT PRODUCTCODE, PRODUCTNAME, PRODUCTPRICE, BRANDNAME, ",
        "ORDERQUANTITY, to_char(ORDERDATE,'YYYY-MM-DD') AS ORDERDATE, MERCHANT_UID ",
        " FROM PAYHISTORYLIST  WHERE MEMBER=#{email}",
        "ORDER BY ORDERDATE DESC"
    })
    public List<Map<String, Object>> selectPayMemberList(@Param("email") String email);

    // 리뷰 작성 시 결제내역의 리뷰확인 해당하는거 다 true로 변경해주기
    @Update({
        "UPDATE PAYHISTORY SET REVIEWCHECK=TRUE WHERE PRODUCT=#{no} AND MEMBER=#{email}"
    })
    public void updateReview(@Param("no") Long no, @Param("email") String email);

    // 환불 시 주문내역의 정보 삭제
    @Delete({
        "DELETE FROM PAYHISTORY WHERE PAY=(",
        "SELECT IMP_UID FROM PAY WHERE MERCHANT_UID=#{id})",
        "AND PRODUCT=#{code}"
    })
    public void deletePayHistory(@Param("id") String id, @Param("code") long code);
}
