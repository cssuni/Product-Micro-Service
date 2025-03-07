package com.product_Micro.service.product;





import com.product_Micro.Model.Product;
import com.product_Micro.dto.ProductDto;
import com.product_Micro.request.AddProductRequest;

import java.util.List;

public interface IProductService {
    ProductDto addProduct(AddProductRequest request);
//
//    Product updateProduct(AddProductRequest request, Long Id);
//
//    Product updateProduct(AddProductRequest request, Long Id);

    Product updateProduct(AddProductRequest request, Long Id);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    List<ProductDto> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String category, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> addBulkProduct(List<AddProductRequest> products);
}
