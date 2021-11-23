package com.example.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CancelHistoryMapper {
    
    // 환불내역
    @Select({
        "<script>",
            "SELECT CANCELHISTORY.CANCELQUANTITY, to_char(CANCELHISTORY.CANCELDATE, 'YYYY-MM-DD') AS CANCELDATE, PRODUCT.PRODUCTPRICE,",
            " PRODUCT.PRODUCTNAME, PRODUCT.PRODUCTCODE, BRAND.BRANDNAME,PAY.MERCHANT_UID FROM CANCELHISTORY ",
            " INNER JOIN PRODUCT ON CANCELHISTORY.PRODUCT = PRODUCT.PRODUCTCODE",
            " INNER JOIN BRAND ON PRODUCT.BRAND = BRAND.BRANDCODE",
            " INNER JOIN PAY ON CANCELHISTORY.PAY = PAY.IMP_UID",
            " WHERE CANCELHISTORY.MEMBER=#{email} ",
        "</script>"
    })
    public List<Map<String, Object>> selectCancelMemberList(@Param("email") String email);
}
