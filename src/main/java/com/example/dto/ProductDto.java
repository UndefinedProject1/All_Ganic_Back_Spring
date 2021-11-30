package com.example.dto;

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
public class ProductDto {
    private long productcode = 0L;
    private String productname = null;
    private long productprice = 0L;
    private String brandname;
}
