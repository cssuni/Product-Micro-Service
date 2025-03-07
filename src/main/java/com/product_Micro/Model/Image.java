package com.product_Micro.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String downloadUrl;

    @JsonIgnore
    @Lob
    private byte[] data;

    @JsonIgnore
    @ManyToOne
    //FK stored in product_id column in image table
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


}
