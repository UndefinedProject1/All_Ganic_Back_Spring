package com.example.entity;

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
@SequenceGenerator(name = "SEQ_BRAND_NO", sequenceName = "SEQ_BRAND_NO", initialValue = 1, allocationSize = 1)
public class Brand {

    @Id
    @Column(name = "BRANDCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BRAND_NO")
    private Long brandcode = 0L;

    @Column(name = "BRANDNAME")
    private String brandname = null;

    // 이미지
    @Lob
    @Column(name = "BRANDIMAGE")
    private byte[] brandimage = null;

    @Column(name = "IMAGENAME")
    private String imagename = null; // 파일명

    @Column(name = "IMAGETYPE")
    private String imagetype = null;

}
