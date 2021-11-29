package com.example.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RecommendMapper {
    
    // 해당 물품의 추천물품이 있는지 확인
    @Select({
        "SELECT RECOMMENDKEY, RECOMMENDVALUE FROM RECOMMEND WHERE PRODUCT=#{code}"
    })
    public Map<String, Object> checkRecommend(@Param("code")Long code);

    // 추천물품의 key와 value업데이트
    @Update({
        "UPDATE RECOMMEND SET RECOMMENDKEY=#{key}, RECOMMENDVALUE=#{count} WHERE PRODUCT=#{no}"
    })
    public void updateReportDate(@Param("key") String key, @Param("count") String count, @Param("no") Long no);

}
