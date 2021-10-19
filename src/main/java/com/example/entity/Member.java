package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "USEREMAIL", length = 100)
    private String useremail;

    @Column(name = "USERPW")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String userpw = null;

    @Transient // 클럼이 안만들어짐
    private String userpw1 = null;

    @Column(name = "USERNAME")
    private String username = null;

    @Column(name = "USERTEL")
    private String usertel = null;

    @Column(name = "USERROLE", updatable = false)
    private String userrole = null;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(updatable = false, name = "USERDATE")
    private Date userdate = null;

    @Column(name = "POST")
    private Number post = 0;

    @Column(name = "ADDRESS")
    private String address = null;

    // 1 : N의 관계이고, Address 엔티티의 member변수명
    // @OneToMany(mappedBy = "member")
    // @OrderBy("no asc")
    // private List<Address> addressList = new ArrayList<>();
    // Address에서 외래키만 걸리면 되는거고 해당 엔티티에는 딱히 안해줘도 됨.(Address Repository에 추가했음)
}
