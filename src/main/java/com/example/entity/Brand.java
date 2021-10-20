package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "BRAND")
public class Brand {
    
    @Id
    @Column(name = "BRANDCODE")
    private Long brandcode = 0L;

    @Column(name = "BRANDNAME")
    private String brandname = null;

    // 이미지
    @Lob
    @Column(name = "BRANDIMAGE")
    private byte[] image = null;

    @Column(name = "IMAGENAME")
    private String imagename = null; // 파일명

    @Column(name = "IMAGETYPE")
    private String imagetype = null;

    @ManyToOne //관리자 정보
    @JoinColumn(name = "ADMIN")
    private Member member;

}
