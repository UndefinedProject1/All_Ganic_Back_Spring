package com.example.service;

import java.util.List;

import com.example.entity.SubImage;
import com.example.entity.SubImageProjection;

public interface SubImageService {

    // 서브 이미지 추가
    public void insertSubimg(List<SubImage> list);

    // 물품 코드에 따른 서브 이미지들 찾기 
    public List<SubImageProjection> selectSubcode(long code);

    // 서브 이미지 찾고 변환하기
    public SubImage selectSubimg(long code);

    //서브이미지 수정
    public void updateSubimg(List<SubImage> list);

    //서브이미지 삭제
    public void deleteSubimg(long no);
}
