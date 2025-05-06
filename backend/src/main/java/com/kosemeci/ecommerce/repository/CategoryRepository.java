package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    Category findByCategoryId(String categoryId);
}
