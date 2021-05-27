package com.randikalakmal.customerservice.repository;

import com.randikalakmal.customerservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    int countByCategory(String category);

    Optional<Category> findCategoryByCategory(String category);

    List<Category> findByCategoryIgnoreCaseContaining(String category);

    void deleteCategoryByCategoryid(Integer id);
}
