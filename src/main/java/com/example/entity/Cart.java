package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CART")
@SequenceGenerator(name = "SEQ_CART_NO", sequenceName = "SEQ_CART_NO", initialValue = 1, allocationSize = 1)
public class Cart {
    @Id
    @Column(name = "CARTCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CART_NO")
    private long cartcode = 0L;

    @OneToOne // 회원정보
    @JoinColumn(updatable = false, name = "MEMBER")
    private Member member;

    @ManyToOne // 물품 정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

    @Column(name = "QUANTITY")
    private Long quantity = 0L;

}
