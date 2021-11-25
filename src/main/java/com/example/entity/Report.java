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
@Table(name = "REPORT")
@SequenceGenerator(name = "SEQ_REPORT_NO", sequenceName = "SEQ_REPORT_NO", initialValue = 1, allocationSize = 1)
public class Report {
    
    @Id
    @Column(name = "REPORTCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPORT_NO")
    private long reportcode = 0L;

    @OneToOne // 회원정보
    @JoinColumn(updatable = false, name = "MEMBER")
    private Member member;

    @Column(name = "REPORTDATE")
    private String reprotdate = null;

    @Column(name = "REPORTCOUNT")
    private Long reportcount = 0L;
}
