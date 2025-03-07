package com.product_Micro.dto;



import com.product_Micro.Model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
//    private List<ImageDto> images;

}
