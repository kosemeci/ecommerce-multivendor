package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);
}
