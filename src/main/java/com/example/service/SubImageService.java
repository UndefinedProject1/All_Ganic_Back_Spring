package com.example.service;

import com.example.entity.SubImage;

public interface SubImageService {

    // 서브 이미지 추가
    public void insertSubimg(SubImage subimg);

    // 서브 이미지 찾기
    public SubImage selectsubimg(String no);
}
