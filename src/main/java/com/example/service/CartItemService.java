package com.example.service;

import java.util.List;
import java.util.Map;

import com.example.entity.CartItem;

import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    
    // 장바구니 물품 추가
    public void insertCartItem(CartItem cartitem);

    // 장바구니에 담긴 물품코드 중복 확인
    public int checkProduct(Long no, Long code);

    // 장바구니아이템 1개 찾기(여기서 code는 cart코드)
    public CartItem selectCartProductOne(long no, long code);

    // 장바구니아이템 수량 업데이트
    public void updateQuantity(long cnt, long no);

    // 회원별 장바구니 아이템 리스트 출력
    public List<Map<String, Object>> selectMemberCartList(String email);

    // 회원 장바구니 아이템 전체삭제
    public int deleteCartItemAll(Long code);

    // 회원 장바구니 아이템 선택삭제
    public void deleteCartItemSome(List<Long> chks);

}
