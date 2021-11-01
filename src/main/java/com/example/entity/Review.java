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
@Table(name = "REVIEW")
@SequenceGenerator(name = "SEQ_REVIEW_NO", sequenceName = "SEQ_REVIEW_NO", initialValue = 1, allocationSize = 1)
public class Review {
    @Id
    @Column(name = "REVIEWCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW_NO")
    private long reviewcode = 0L;

    @Column(name = "REVIEWCONTENT")
    private String reviewcontent = null;

    @Column(name = "REVIEWRATING", nullable = false)
    private long reviewrating = 0L;

    @Lob
    @Column(name = "REVIEWIMAGE")
    private byte[] reviewimg = null;

    @Column(name = "REVIEWIMGNAME")
    private String reviewimgname = null; // 파일명

    @Column(name = "REVIEWIMGTYPE")
    private String reviewimgtype = null;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(updatable = false, name = "REVIEWDATE")
    private Date reviewdate = null;

    @ManyToOne // 물품 정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

    @ManyToOne // 회원 정보
    @JoinColumn(updatable = false, name = "MEMBER")
    private Member member;

}
