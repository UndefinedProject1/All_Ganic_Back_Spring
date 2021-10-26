package com.example.service;

import java.util.List;

import com.example.entity.SubImage;

public interface SubImageService {

    // 서브 이미지 추가
    public void insertSubimg(List<SubImage> list);

    // 서브 이미지 찾기
    public SubImage selectsubimg(Long no);

    //public List<SubImage> findByProductcode(long no);

    //서브이미지 수정
    public void updateSubimg(List<SubImage> list);

    //서브이미지 삭제
    public void deleteSubimg(long no);
}
