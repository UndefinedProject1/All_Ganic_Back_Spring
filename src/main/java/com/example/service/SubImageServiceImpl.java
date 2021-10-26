package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.example.entity.SubImage;
import com.example.repository.SubImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubImageServiceImpl implements SubImageService {

    @Autowired
    EntityManagerFactory emf;
    @Autowired
    SubImageRepository sRepository;

    //이미지 추가
    @Override
    public void insertSubimg(List<SubImage> list) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();//트랜젝션 시작
        for(SubImage subImage:list) {
            em.persist(subImage);
        }
        em.getTransaction().commit();
    }

    //이미지 찾기
    @Override
    public SubImage selectsubimg(Long no) {
        Optional<SubImage> subimg = sRepository.findById(no);
        return subimg.orElse(null);
    }

    //이미지 수정
    @Override
    public void updateSubimg(List<SubImage> list) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();//트랜젝션 시작
         String sql = "UPDATE SubImage SET SUBIMG=:subimg, IMAGENAME=:imagename, IMAGETYPE=:imagetype "
            + "WHERE PRODUCT=:product";
        for(SubImage subImage:list){
            em.createNativeQuery(sql).setParameter("subimg", subImage.getImage()).setParameter("imagename", subImage.getImagename()).
            setParameter("imagetype", subImage.getImagetype()).setParameter("product", subImage.getProduct()).executeUpdate();
        }
        em.getTransaction().commit();
        
    }

    // @Override
    // public List<SubImage> findByProductcode(long no) {
    //     Optional<SubImage> list = sRepository.findById(no);
    //     return list.orElse(null);
    // }

}
