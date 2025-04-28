package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
