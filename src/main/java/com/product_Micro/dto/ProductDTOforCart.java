package com.product_Micro.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTOforCart {

    private String name;
    private Long id;
    private BigDecimal price;
    private String url;

}
