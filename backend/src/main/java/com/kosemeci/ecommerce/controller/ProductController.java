package com.kosemeci.ecommerce.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.entity.Product;
import com.kosemeci.ecommerce.exception.ProductException;
import com.kosemeci.ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ProductException {
        
        Product product = productService.findProductById(id);
        return  ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(
        @RequestParam(required=false) String query) {

        return ResponseEntity.ok(productService.searchProducts(query));
    }

    @GetMapping()
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required=false) String category,
            @RequestParam(required=false) String brand,
            @RequestParam(required=false) String color,
            @RequestParam(required=false) String size,
            @RequestParam(required=false) Integer minPrice,
            @RequestParam(required=false) Integer maxPrice,
            @RequestParam(required=false) Integer minDiscount,
            @RequestParam(required=false) String sort,
            @RequestParam(required=false) String stock,
            @RequestParam(defaultValue = "0") Integer pageNumber){

        return new ResponseEntity<>(
            productService.getAllProducts(category, brand, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber),HttpStatus.OK);
        
    }
    
    
    

}
