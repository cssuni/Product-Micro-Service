package com.product_Micro.request;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class AddImage {

    private Long id;
    private String name;
    private String type;
    @Lob
    private byte[] data;

    private int product;


}
