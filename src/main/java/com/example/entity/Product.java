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
    @Column(name = "PRODCUTCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_NO")
    private long productcode = 0L;

    @Column(name = "PRODCUTNAME")
    private String productname = null;

    @Column(name = "PRODCUTPRICE")
    private long productprice = 0L;

    @Column(name = "PRODCUTCONTENT")
    private String productcontent = null;

    @Column(name = "PRODUCTIMAGE")
    private String productimage = null;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(updatable = false, name = "PRODUCTDATE")
    private Date userdate = null;

    @ManyToOne // 브랜드 정보
    @JoinColumn(updatable = false, name = "BRAND")
    private Brand brand;

    @ManyToOne // 카테고리 정보
    @JoinColumn(updatable = false, name = "CATEGORY")
    private Category category;

}
