package com.soltec.web.controller;

import com.soltec.web.entity.Category;
import com.soltec.web.entity.Product;
import com.soltec.web.entity.StockCurrent;
import com.soltec.web.repository.CategoryRepository;
import com.soltec.web.repository.ProductRepository;
import com.soltec.web.repository.StockCurrentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StockCurrentRepository stockCurrentRepository;

    public CatalogController(CategoryRepository categoryRepository, ProductRepository productRepository, StockCurrentRepository stockCurrentRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.stockCurrentRepository = stockCurrentRepository;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(productRepository.findByCategoryId(categoryId));
    }

    @GetMapping("/inventory/stock")
    public ResponseEntity<List<StockCurrent>> getStock() {
        return ResponseEntity.ok(stockCurrentRepository.findAll());
    }
}
