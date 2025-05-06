package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
     
}
