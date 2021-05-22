package com.randikalakmal.adminservice.service;


import com.randikalakmal.adminservice.exception.CategoryException;
import com.randikalakmal.adminservice.model.Category;
import com.randikalakmal.adminservice.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category){
        categoryValidation(category);
        Category newCategory = setCategoryToLowerCase(category);
        return categoryRepository.save(newCategory);
    }

    public Category updateCategory(Category category){
        categoryValidation(category);
        Category newCategory = setCategoryToLowerCase(category);
        return categoryRepository.save(newCategory);
    }

    public Category getCategoryByCategory(String categoryName){
        return categoryRepository.findCategoryByCategory(categoryName)
                .orElseThrow(()->new CategoryException("Category name :"+categoryName+"was not found"));
    }
    
    public List<Category> getCategoryByCategoryNameLike(String categoryName){
        return categoryRepository.findByCategoryIgnoreCaseContaining(categoryName);
    }


    public void deleteCategoryById(Integer id){
        categoryRepository.deleteCategoryByCategoryid(id);
    }

    public int getCountByCategory(String category){
        return categoryRepository.countByCategory(category);
    }

    private void categoryValidation(Category category){
        String categoryName = category.getCategory().toLowerCase();
        if (getCountByCategory(categoryName)>0){
            throw new CategoryException("Category "+categoryName+" already exist !");
        }
        if (categoryName.isEmpty() || categoryName.isBlank()){
            throw new CategoryException("Category Name cannot be empty or bank");
        }

    }

    private Category setCategoryToLowerCase(Category category){
        category.setCategory(category.getCategory().toLowerCase());
        return category;
    }
}
