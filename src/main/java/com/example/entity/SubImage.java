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
@Table(name = "SUBIMAGE")
@SequenceGenerator(name = "SEQ_SUBIMAGE_NO", sequenceName = "SEQ_SUBIMAGE_NO", initialValue = 1, allocationSize = 1)
public class SubImage {

    @Id
    @Column(name = "SUBCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBIMAGE_NO")
    private long subcode = 0L;

    // 이미지
    @Lob
    @Column(name = "SUBIMG")
    private byte[] image = null;

    @Column(name = "IMAGENAME")
    private String imagename = null; // 파일명

    @Column(name = "IMAGETYPE")
    private String imagetype = null;

    @ManyToOne // 물품 정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

}
