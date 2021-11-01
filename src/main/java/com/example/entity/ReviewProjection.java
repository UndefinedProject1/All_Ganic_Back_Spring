package com.example.entity;

import java.util.Date;

public interface ReviewProjection {
    
    long getReviewcode();

    String getReviewcontent();

    long getReviewrating();

    Date getReviewdate();

}
