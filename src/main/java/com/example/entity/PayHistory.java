package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "PAYHISTORY")
@SequenceGenerator(name = "SEQ_PAYHISTORY_NO", sequenceName = "SEQ_PAYHISTORY_NO", initialValue = 1, allocationSize = 1)
public class PayHistory {
    
    @Id
    @Column(name = "PAYHISTORYCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYHISTORY_NO")
    private long payhistorycode = 0L;

    @Column(name = "ORDERQUANTITY", updatable = false)
    private long orderquantity = 0L;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false, name = "ORDERDATE")
    private Date orderdate = null;

    @ManyToOne // 결제 정보
    @JoinColumn(updatable = false, name = "PAY")
    private Pay pay;

    @ManyToOne // 회원 정보
    @JoinColumn(updatable = false, name = "MEMBER")
    private Member member;

    @ManyToOne // 물품 정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;
}
