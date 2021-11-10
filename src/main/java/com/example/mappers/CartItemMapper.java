package com.example.mappers;

import java.util.List;
import java.util.Map;

import com.example.entity.CartItem;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CartItemMapper {
    // 장바구니에 담긴 물품코드 중복확인(회원이메일도 체크)
    @Select({"SELECT COUNT(CART) FROM CARTITEM ", 
    "WHERE PRODUCT=#{no} AND CART=#{code}"})
	public int selectCount(@Param("no") Long no, @Param("code") Long code);

    // 장바구니물품 찾아서 반환하기
    @Select({"SELECT * FROM CARTITEM ", 
    "WHERE PRODUCT=#{no} AND CART=#{code}"})
	public CartItem selectCartItem(@Param("no") Long no, @Param("code") Long code);

    // 수량 변경하기
    @Update({
        "UPDATE CARTITEM SET QUANTITY=#{cnt} WHERE CARTITEMCODE=#{no}"
    })
    public void updateQuantity(@Param("cnt") Long cnt, @Param("no") Long no);

    // 회원별 장바구니아이템 리스트 출력
    @Select({
        "<script>",
            "SELECT CARTITEM.CARTITEMCODE, CARTITEM.QUANTITY, PRODUCT.PRODUCTCODE, PRODUCT.PRODUCTPRICE",
            "FROM CARTITEM ",
            " INNER JOIN CART ON CARTITEM.CART = CART.CARTCODE",
            " INNER JOIN PRODUCT ON CARTITEM.PRODUCT = PRODUCT.PRODUCTCODE",
            " WHERE CART.MEMBER=#{email} ",
            "ORDER BY PRODUCT.PRODUCTNAME ASC",
        "</script>"
    })
    public List<Map<String, Object>> selectMemberList(String email);

    // 회원 장바구니 전체삭제
    @Delete({
        "DELETE CARTITEM WHERE CART=#{code}"
    })
    public int deleteCartItemAll(@Param("code")long code);
}
