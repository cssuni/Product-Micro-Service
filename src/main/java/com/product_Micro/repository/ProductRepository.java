package com.product_Micro.repository;




import com.product_Micro.Model.Category;
import com.product_Micro.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    Long countByBrandAndName(String brand, String name);

    List<Product> findByName(String name);

    List<Product> findByBrand(String brand);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByBrandAndCategory(String brand, Category category);

    List<Product> findByCategory(Category category1);

    Long countByBrandAndCategory(String brand, Category category);


}
