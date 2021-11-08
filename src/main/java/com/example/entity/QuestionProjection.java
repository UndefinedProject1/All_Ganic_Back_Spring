package com.example.entity;

import java.util.Date;

public interface QuestionProjection {
    
    Long getQuestioncode();

    String getQuestiontitle();

    String getQuestioncontent();

    Date getQuestiondate();

    Long getProductcode();
}


