package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "PRODUCT")
@SequenceGenerator(name = "SEQ_PRODUCT_NO", sequenceName = "SEQ_PRODUCT_NO", initialValue = 1, allocationSize = 1)
public class Product {
    @Id
    @Column(name = "PRODUCTCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_NO")
    private long productcode = 0L;

    @Column(name = "PRODUCTNAME")
    private String productname = null;

    @Column(name = "PRODUCTPRICE")
    private long productprice = 0L;

    // 이미지
    @Lob
    @Column(name = "PRODUCTIMAGE")
    private byte[] image = null;

    @Column(name = "IMAGENAME")
    private String imagename = null; // 파일명

    @Column(name = "IMAGETYPE")
    private String imagetype = null;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(updatable = false, name = "PRODUCTDATE")
    private Date productdate = null;

    @ManyToOne // 브랜드 정보
    @JoinColumn( name = "BRAND")
    private Brand brand;

    @ManyToOne // 카테고리 정보
    @JoinColumn( name = "CATEGORY")
    private Category category;

}
