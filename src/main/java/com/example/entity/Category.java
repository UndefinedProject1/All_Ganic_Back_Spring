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
@Table(name = "CATEGORY")
public class Category {
    
    @Id
    @Column(name = "CATEGORYCODE")
    private Long categorycode = 0L;
    
    @Column(name = "CATEGORYNAME")
    private String categoryname = null;

    @ManyToOne //관리자 정보
    @JoinColumn(name = "ADMIN")
    private Member member;

}
