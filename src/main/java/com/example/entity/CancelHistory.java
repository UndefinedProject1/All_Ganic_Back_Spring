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
@Table(name = "CANCELHISTORY")
@SequenceGenerator(name = "SEQ_CANCELHISTORY_NO", sequenceName = "SEQ_CANCELHISTORY_NO", initialValue = 1, allocationSize = 1)
public class CancelHistory {
    
    @Id
    @Column(name = "CANCELHISTORYCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CANCELHISTORY_NO")
    private long cancelhistorycode = 0L;

    @Column(name = "CANCELQUANTITY", updatable = false)
    private long cancelquantity = 0L;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false, name = "CANCELDATE")
    private Date canceldate = null;

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
