package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "SUBIMAGE")
public class SubImage {

    @Id
    @Column(name = "SUBCODE")
    private String subcode = null;

    @Column(name = "SUBIMG")
    private String subimg = null;

    @ManyToOne // 물품 정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

}
