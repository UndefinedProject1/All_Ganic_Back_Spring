package com.example.mappers;

import com.example.entity.Pay;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayMapper {
    
    // 취소내역을 위한 Pay반환
    @Select({
        "SELECT * FROM PAY WHERE IMP_UID=(",
        "SELECT PAY FROM PAYHISTORY INNER JOIN PAY ON PAYHISTORY.PAY = PAY.IMP_UID",
        "WHERE PAY.MERCHANT_UID=#{id} AND PRODUCT=#{code})"
    })
    public Pay selectImpUid(@Param("id") String id, @Param("code") long code);
}
