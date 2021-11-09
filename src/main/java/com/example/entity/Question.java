package com.example.entity;

import java.util.Date;

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
@Table(name = "QUESTION")
@SequenceGenerator(name = "SEQ_QUESTION_NO", sequenceName = "SEQ_QUESTION_NO", initialValue = 1, allocationSize = 1)
public class Question {
    @Id
    @Column(name = "QUESTIONCODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUESTION_NO")
    private long questioncode = 0L;

    @Column(name = "QUESTIONTITLE", nullable = false)
    private String questiontitle = null;

    @Column(name = "QUESTIONCONTENT")
    private String questioncontent = null;

    @Column(name = "QUESTIONKIND", nullable = false)
    private long questionkind = 0L;

    @Column(name = "QUESTIONREPLY")
    private boolean questionreply = false;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(updatable = false, name = "QUESTIONDATE")
    private Date questiondate = null;

    @ManyToOne // 물품 정보
    @JoinColumn(updatable = false, name = "PRODUCT")
    private Product product;

    @ManyToOne // 회원 정보
    @JoinColumn(updatable = false, name = "MEMBER")
    private Member member;

    @Column(name = "ANSWERCONTENT")
    private String answercontent = null;

    @Column(name = "ANSWERDATE")
    private String answerdate = null;
}
