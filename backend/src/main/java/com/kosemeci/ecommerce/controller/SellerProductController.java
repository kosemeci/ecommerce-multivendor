package com.kosemeci.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.kosemeci.ecommerce.entity.Product;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.exception.SellerException;
import com.kosemeci.ecommerce.service.ProductService;
import com.kosemeci.ecommerce.service.SellerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kosemeci.ecommerce.exception.ProductException;
import com.kosemeci.ecommerce.request.CreateProductRequest;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sellers")
public class SellerProductController {
    
    private final ProductService productService;
    private final SellerService sellerService;

    @GetMapping("path")
    public ResponseEntity<List<Product>> getProductBySellerId(
            @RequestHeader("Authorization") String jwt) throws SellerException {

        Seller seller = sellerService.getSellerByToken(jwt);
        List<Product> products = productService.getProductBySellerId(seller.getId());

        return ResponseEntity.ok(products);
    }

    @PostMapping("path")
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("Authorization") String jwt) throws SellerException {
        
        Seller seller = sellerService.getSellerByToken(jwt);   
        Product product = productService.createProduct(request, seller);

        return new ResponseEntity<>(product,HttpStatus.CREATED);    
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }    

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product product) throws ProductException{
                
        Product updateProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }   
    
}
