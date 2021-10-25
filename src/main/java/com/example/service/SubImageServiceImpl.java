package com.example.service;

import java.util.Optional;

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

    @Override
    public void insertSubimg(SubImage subimg) {
        sRepository.save(subimg);
    }

    @Override
    public SubImage selectsubimg(String no) {
        Optional<SubImage> subimg = sRepository.findById(no);
        return subimg.orElse(null);
    }

}
