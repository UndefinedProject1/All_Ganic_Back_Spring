package com.example.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReviewMapper {
    
    // 물품 삭제 시 관련 리뷰도 삭제
    @Delete({
        "DELETE FROM REVIEW WHERE PRODUCT=#{no}"
    })
    public int deleteProductReview(@Param("no") long no);
}
