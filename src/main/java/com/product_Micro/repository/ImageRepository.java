package com.product_Micro.repository;


import com.product_Micro.Model.Image;
import com.product_Micro.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByProduct(Product product);
}
