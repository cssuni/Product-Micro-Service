package com.product_Micro.service.product;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_Micro.Model.Product;
import com.product_Micro.dto.ProductDto;
import com.product_Micro.exception.ProductNotFoundException;
import com.product_Micro.repository.CategoryRepository;
import com.product_Micro.repository.ProductRepository;
import com.product_Micro.request.AddProductRequest;
import com.product_Micro.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.product_Micro.Model.Category;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final RedisService redisService;

//    private final ImageRepository imageRepository;

    @Override
    public List<ProductDto> addBulkProduct(List<AddProductRequest> products){

        return products.stream().map(this::addProduct).toList();

    }

    @Override
    public ProductDto addProduct(AddProductRequest request) {

        Product product = new Product();
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setName(request.getName());
        product.setInventory(request.getInventory());

        Category category = (Category) Optional.ofNullable(categoryRepository.findByName(request.getCategory()) )
                .orElseGet(() -> {
                    Category newCategory =  new Category();
                    newCategory.setName(request.getCategory());

                    return  newCategory;
                });

        product.setCategory(category);

      return  productToDto(productRepository.save(product));

    }

    @Override
    public Product updateProduct(AddProductRequest request, Long Id) {


        return  productRepository.findById(Id)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product now found!!!")) ;


    }

    private Product updateExistingProduct(Product existingProduct, AddProductRequest request) {

            existingProduct.setBrand(request.getBrand());
            existingProduct.setName(request.getName());
            existingProduct.setDescription(request.getDescription());
            existingProduct.setInventory(request.getInventory());
            existingProduct.setPrice(request.getPrice());

        Category category = (Category) Optional.ofNullable(categoryRepository.findByName(request.getCategory()) )
                .orElseGet(() -> {
                    Category newCategory =  new Category();
                    newCategory.setName(request.getCategory());

                    return  newCategory;
                });

        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public ProductDto getProductDtoById(Long id)  {
        ProductDto productDto = redisService.findProductInRedis(id);
        if( productDto!= null)
            return productDto;

        return productRepository.findById(id)
                .map(product -> {
                                    ProductDto productDto1 = productToDto(product);
                                    redisService.storeProductDtoInRedis(productDto1);
                                    return productDto1;
        }).orElseThrow(() -> new ProductNotFoundException("Product now found!!!"));
    }

    public Product getProductById(Long id) {
        return productRepository.
                findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found!!!"));
    }

    @Override
    public void deleteProductById(Long id) {

        productRepository.findById(id)
                .map(product-> {
                                product.setCategory(null);
                                return product;
                                })
                .map(productRepository::save)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ProductNotFoundException("Product now found!!!");

                        });
    }

    @Override
    public List<ProductDto> getAllProducts() {
        System.out.println("Service Called");
        System.out.println(productRepository.findAll());
        List<ProductDto>  productDtos =  productRepository.findAll().stream()
                .map(this::productToDto)
                .toList();
        System.out.println(productDtos);
        return productDtos;
    }

    public ProductDto productToDto(Product product){

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {

        Category category1 = categoryRepository.findByName(category );
        return productRepository.findByCategory(category1);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {

        Category category1 = categoryRepository.findByName(category );
        return productRepository.findByBrandAndCategory(brand,category1);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {

        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        Category category = categoryRepository.findByName(name);
        return productRepository.countByBrandAndCategory(brand,category);
    }

    @Override
    public List<ProductDto> searchProducts(String searchTerm) {
        List<Product> products = productRepository.searchProducts(searchTerm);
        if(!products.isEmpty()) {
            return products.stream().map(this::productToDto).toList();
        } else {
            throw new ProductNotFoundException("Products not found");
        }

    }



}
