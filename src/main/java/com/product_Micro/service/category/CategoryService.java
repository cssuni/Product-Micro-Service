package com.product_Micro.service.category;



import com.product_Micro.exception.CategoryAlreadyExistsException;
import com.product_Micro.exception.ResourceNotFoundException;
import com.product_Micro.repository.CategoryRepository;
import com.product_Micro.service.category.ICategoryService;
import com.product_Micro.Model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;


    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category now found!!!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {



        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new CategoryAlreadyExistsException("Category Already Exists!!!"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {


        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                }).orElseThrow(() -> new ResourceNotFoundException("Category now found!!!"));
    }

    @Override
    public void deleteCategoryById(Long id) {

        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                () -> { throw new ResourceNotFoundException("Category Not Found"); }
                );

    }


//    public List<Product> getAllProductsByCategoryName(String name) {
//        return List.of();
//    }
//
//    public List<Product> getAllProductsByCategoryName(Long id) {
//        return List.of();
//    }


}
