package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.entity.CartItem;
import com.example.mappers.CartItemMapper;
import com.example.repository.CartItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    CartItemRepository ciRepository;

    @Autowired
    CartItemMapper ciMapper;

    // 장바구니 물품 추가
    @Override
    public void insertCartItem(CartItem cartitem) {
        ciRepository.save(cartitem);
    }

    // 장바구니에 담긴 물품코드 중복확인
    @Override
    public int checkProduct(Long no, Long code) {
        return ciMapper.selectCount(no, code);
    }

    // 장바구니 아이템 수량 리턴
    @Override
    public Long selectCartQuantity(long code) {
        return ciMapper.selectCartQuantity(code);
    }
   
    // 장바구니 아이템 찾고 반환하기
    @Override
    public CartItem selectCartProductOne(long no, long code) {
        return ciMapper.selectCartItem(no, code);
    }

    // 장바구니 수량 업데이트
    @Override
    public void updateQuantity(long cnt, long no) {
        ciMapper.updateQuantity(cnt, no);
    }

    // 회원별 장바구니 리스트 출력
    @Override
    public List<Map<String, Object>> selectMemberCartList(String email) {
        return ciMapper.selectMemberList(email);
    }

    // 회원 장바구니 아이템 전체삭제
    @Override
    public int deleteCartItemAll(Long code) {
        return ciMapper.deleteCartItemAll(code);
    }

    // 회원 장바구니 아이템 선택삭제
    @Override
    public void deleteCartItemSome(List<Long> chks) {
        ciRepository.deleteAllByIdInBatch(chks);
    }

    @Override
    public List<Map<String, Object>> payMemberProductList(List<Long> chks) {
        return ciMapper.selectPaymentInfo(chks);
    }

    // 물품 삭제 시 장바구니 아이템 삭제
    @Override
    public int deleteProductCartItem(Long no) {
        return ciMapper.deleteProductCartItem(no);
    }

    
}
