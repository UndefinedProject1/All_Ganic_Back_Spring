package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "CARTITEM")
@SequenceGenerator(name = "SEQ_CARTITEM_NO", sequenceName = "SEQ_CARTITEM_NO", initialValue = 1, allocationSize = 1)
public class CartItem {
    @Id
    @Column(name = "CARTITEMCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARTITEM_NO")
    private long cartitemcode = 0L;

    @ManyToOne // 카트 정보
    @JoinColumn(updatable = false, name = "CART")
    private Cart cart;
    
    @ManyToOne // 물품 정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

    @Column(name = "QUANTITY")
    private Long quantity = 0L;

}
