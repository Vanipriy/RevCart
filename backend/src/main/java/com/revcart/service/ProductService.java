package com.revcart.service;

import com.revcart.entity.Product;
import com.revcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch products: " + e.getMessage());
        }
    }
    
    public Page<Product> getAllProducts(Pageable pageable) {
        try {
            return productRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch products: " + e.getMessage());
        }
    }
    
    public Optional<Product> getProductById(Long id) {
        try {
            return productRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    public List<Product> getProductsByCategory(String category) {
        try {
            return productRepository.findByCategoryIgnoreCase(category);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch products by category: " + e.getMessage());
        }
    }
    
    public List<Product> searchProducts(String query) {
        try {
            return productRepository.findByNameContainingIgnoreCase(query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to search products: " + e.getMessage());
        }
    }
    
    @Transactional
    public Product saveProduct(Product product) {
        try {
            if (product.getCreatedAt() == null) {
                product.setCreatedAt(LocalDateTime.now());
            }
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save product: " + e.getMessage());
        }
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new RuntimeException("Product not found with id: " + id);
            }
            productRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete product: " + e.getMessage());
        }
    }
    
    @Transactional
    public boolean reduceStock(Long productId, Integer quantity) {
        try {
            Product product = productRepository.findById(productId).orElse(null);
            if (product == null || product.getStock() < quantity) {
                return false;
            }
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}