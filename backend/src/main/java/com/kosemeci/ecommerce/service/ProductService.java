package com.kosemeci.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.kosemeci.ecommerce.entity.Product;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.exception.ProductException;
import com.kosemeci.ecommerce.request.CreateProductRequest;

public interface ProductService {
    
    public Product createProduct(CreateProductRequest request,Seller seller);
    public void deleteProduct(Long id) throws ProductException;
    public Product updateProduct(Long productId,Product product) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    public List<Product> searchProducts(String query);
    public Page<Product> getAllProducts(
        String category,
        String brand,
        String colors,
        String sizes,
        Integer minPrice,
        Integer maxPrice,
        Integer minDiscount,
        String sort,
        String stock,
        Integer pageNumber
    );
    public List<Product> getProductBySellerId(Long id);

}
