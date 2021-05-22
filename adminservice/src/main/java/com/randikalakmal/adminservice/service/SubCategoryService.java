package com.randikalakmal.adminservice.service;

import com.randikalakmal.adminservice.exception.CategoryException;
import com.randikalakmal.adminservice.exception.SubCategoryException;
import com.randikalakmal.adminservice.model.SubCategory;
import com.randikalakmal.adminservice.repository.SubCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    public List<SubCategory> getAllSubCategory(){
        return subCategoryRepository.findAll();
    }

    public SubCategory getSubCategoryBySubCategory(String subCategory){
        return subCategoryRepository.findSubCategoryBySubCategoryName(subCategory)
                .orElseThrow(()->new SubCategoryException("Sub Category "+subCategory+" Not found"));
    }

    public List<SubCategory> getSubCategoryLikeSubCategoryName(String subCategory){
        return subCategoryRepository.findBySubCategoryNameIgnoreCaseContaining(subCategory);
    }

    public SubCategory addSubCategory(SubCategory subCategory){
        subCategoryValidation(subCategory);
        SubCategory newSubCategory = setSubCategoryToLowerCase(subCategory);
        return subCategoryRepository.save(newSubCategory);
    }
    public SubCategory updateSubCategory(SubCategory subCategory){
        subCategoryValidation(subCategory);
        SubCategory newSubCategory = setSubCategoryToLowerCase(subCategory);
        return subCategoryRepository.save(newSubCategory);
    }

    public void deleteSubCategory(Integer id){
        subCategoryRepository.deleteSubcategoryByIdsubCategory(id);
    }


    public int getCountBySubCategory(String subCategory){

        return subCategoryRepository.countBySubCategoryName(subCategory);
    }

    private void subCategoryValidation(SubCategory subCategory){
        String subCategoryName = subCategory.getSubCategoryName().toLowerCase();
        if (getCountBySubCategory(subCategoryName)>0){
            throw new CategoryException("Sub Category "+subCategoryName+" already exist !");
        }
        if (subCategoryName.isEmpty() || subCategoryName.isBlank()){
            throw new CategoryException("Sub Category Name cannot be empty or bank");
        }

    }

    private SubCategory setSubCategoryToLowerCase(SubCategory subCategory){
        subCategory.setSubCategoryName(subCategory.getSubCategoryName().toLowerCase());
        return subCategory;
    }
}
