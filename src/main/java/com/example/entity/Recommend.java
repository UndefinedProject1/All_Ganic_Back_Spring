package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "RECOMMEND")
@SequenceGenerator(name = "SEQ_RECOMMEND_NO", sequenceName = "SEQ_RECOMMEND_NO", initialValue = 1, allocationSize = 1)
public class Recommend {

    @Id
    @Column(name = "RECOMMENDCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECOMMEND_NO")
    private long RECOMMENDcode = 0L;

    @OneToOne // 물품정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

    @Column(updatable = false, name = "RECOMMENDKEY")
    private Long recommendkey = 0L;

    @Column(name = "RECOMMENDVALUE")
    private Long recommendvalue = 0L;
}