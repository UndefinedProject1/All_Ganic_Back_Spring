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
public class CartItemDTO {
    private long cartitemcode = 0L;
    private long cart = 0L;
    private long product = 0L;
    private long quantity = 0L;
}
