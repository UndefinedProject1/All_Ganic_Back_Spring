package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@Table(name = "PAY")
public class  Pay{
    
    @Id
    @Column(name = "IMP_UID", length = 100)
    private String imp_uid;

    @Column(name = "MERCHANT_UID", updatable = false)
    private String merchant_uid = null;

    @Column(name = "ORDERQUANTITY", updatable = false)
    private String orderquantity = null;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(updatable = false, name = "ORDERDATE")
    private Date orderdate = null;

    @OneToMany(mappedBy = "pay")
	private List<Product> products = new ArrayList<>();

    @ManyToOne // 회원 정보
    @JoinColumn(updatable = false, name = "MEMBER")
    private Member member;

}
