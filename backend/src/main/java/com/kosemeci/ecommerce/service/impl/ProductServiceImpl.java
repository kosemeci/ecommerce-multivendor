package com.kosemeci.ecommerce.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.entity.Category;
import com.kosemeci.ecommerce.entity.Product;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.exception.ProductException;
import com.kosemeci.ecommerce.repository.CategoryRepository;
import com.kosemeci.ecommerce.repository.ProductRepository;
import com.kosemeci.ecommerce.request.CreateProductRequest;
import com.kosemeci.ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements  ProductService{
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    @Override
    public Product createProduct(CreateProductRequest request, Seller seller) {
        
        Category category1 = categoryRepository.findByCategoryId(request.getCategory());

        if(category1 == null){
            Category category = new Category();
            category.setCategoryId(request.getCategory());
            category.setLevel(1);
            category1 = categoryRepository.save(category1);
        }
        Category category2 = categoryRepository.findByCategoryId(request.getCategory2());

        if(category2 == null){
            Category category = new Category();
            category.setCategoryId(request.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepository.save(category2);
        }
        Category category3 = categoryRepository.findByCategoryId(request.getCategory3());

        if(category2 == null){
            Category category = new Category();
            category.setCategoryId(request.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepository.save(category3);
        }

        int discountPercentage = calculateDiscountPercentage(request.getMrpPrice(),request.getSellingPrice());

        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category3);
        product.setDescription(request.getDescription());
        product.setCreatedAt(LocalDateTime.now());
        product.setColor(request.getColor());
        product.setTitle(request.getTitle());
        product.setSellingPrice(request.getSellingPrice());
        product.setImages(request.getImages());
        product.setMrpPrice(request.getMrpPrice());
        product.setSizes(request.getSizes());
        product.setDiscountPercentage(discountPercentage);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) throws ProductException {
        
        Product product = findProductById(id);
        productRepository.delete(product);

    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductException {
        
        findProductById(productId);
        product.setId(productId);
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        
        return productRepository.findById(productId).orElseThrow(()-> new ProductException("product not found with id "+ productId));
    }

    @Override
    public List<Product> searchProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchProducts'");
    }

    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
    }

    @Override
    public List<Product> getProductBySellerId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductBySellerId'");
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        
        if(mrpPrice<=0){
            throw new IllegalArgumentException("Mrp must be greater than zero");
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;
        return  (int)discountPercentage;
    }
    
}
