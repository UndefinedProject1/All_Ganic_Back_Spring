package com.example.dto;

import java.util.Date;

import com.example.entity.Member;
import com.example.entity.Product;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QuestionDTO {
	private long questioncode = 0L;
	private String questiontitle = null;
	private String questioncontent = null;
    private long questionkind = 0L;
    private boolean questionreply = false;
    private Date questiondate = null;
    
    private long productcode = 0L;
    private String productname = null;

}