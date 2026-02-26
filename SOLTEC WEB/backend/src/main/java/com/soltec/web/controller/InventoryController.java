package com.soltec.web.controller;

import com.soltec.web.entity.Product;
import com.soltec.web.entity.Category;
import com.soltec.web.entity.StockCurrent;
import com.soltec.web.repository.ProductRepository;
import com.soltec.web.repository.CategoryRepository;
import com.soltec.web.repository.StockCurrentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private StockCurrentRepository stockCurrentRepository;

    @GetMapping("/products")
    public List<Map<String, Object>> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        List<Product> products;
        if (search != null && !search.isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCase(search);
        } else if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategoryId(category);
        } else {
            products = productRepository.findAllOrderByName();
        }
        return products.stream().map(this::mapProduct).collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable String id) {
        return productRepository.findById(id)
                .map(p -> ResponseEntity.ok(mapProductDetail(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable String id, @RequestBody Map<String, Object> body) {
        return productRepository.findById(id).map(p -> {
            if (body.containsKey("name")) p.setName((String) body.get("name"));
            if (body.containsKey("pricesell")) p.setPricesell(((Number) body.get("pricesell")).doubleValue());
            if (body.containsKey("pricebuy")) p.setPricebuy(((Number) body.get("pricebuy")).doubleValue());
            if (body.containsKey("categoryId")) p.setCategoryId((String) body.get("categoryId"));
            if (body.containsKey("isservice")) p.setIsservice((Boolean) body.get("isservice"));
            productRepository.save(p);
            return ResponseEntity.ok(mapProductDetail(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    public Map<String, Object> createProduct(@RequestBody Map<String, Object> body) {
        Product p = new Product();
        p.setId(UUID.randomUUID().toString());
        p.setReference((String) body.getOrDefault("reference", p.getId().substring(0, 8)));
        p.setCode((String) body.getOrDefault("code", p.getReference()));
        p.setName((String) body.get("name"));
        p.setPricesell(((Number) body.getOrDefault("pricesell", 0)).doubleValue());
        p.setPricebuy(((Number) body.getOrDefault("pricebuy", 0)).doubleValue());
        p.setCategoryId((String) body.getOrDefault("categoryId", "000"));
        p.setTaxcatId((String) body.getOrDefault("taxcatId", "001"));
        p.setIsservice(body.containsKey("isservice") ? (Boolean) body.get("isservice") : false);
        p.setPrintto((String) body.getOrDefault("printto", "1"));
        productRepository.save(p);
        return mapProductDetail(p);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            Map<String, String> response = new LinkedHashMap<>();
            response.put("status", "deleted");
            response.put("id", id);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categories")
    public List<Map<String, Object>> getCategories() {
        return categoryRepository.findAllByOrderByNameAsc().stream().map(c -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", c.getId());
            map.put("name", c.getName());
            map.put("parentId", c.getParentId());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/stock")
    public List<Map<String, Object>> getStock() {
        return stockCurrentRepository.findAllWithProduct().stream().map(sc -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("productId", sc.getProductId());
            map.put("productName", sc.getProduct() != null ? sc.getProduct().getName() : "N/A");
            map.put("location", sc.getLocation());
            map.put("units", sc.getUnits());
            return map;
        }).collect(Collectors.toList());
    }

    private Map<String, Object> mapProduct(Product p) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", p.getId());
        map.put("reference", p.getReference());
        map.put("code", p.getCode());
        map.put("name", p.getName());
        map.put("pricesell", p.getPricesell());
        map.put("categoryId", p.getCategoryId());
        map.put("isservice", p.isIsservice());
        return map;
    }

    private Map<String, Object> mapProductDetail(Product p) {
        Map<String, Object> map = mapProduct(p);
        map.put("pricebuy", p.getPricebuy());
        map.put("stockunits", p.getStockunits());
        map.put("taxcatId", p.getTaxcatId());
        map.put("printto", p.getPrintto());
        map.put("iscom", p.isIscom());
        map.put("printkb", p.isPrintkb());
        map.put("sendstatus", p.isSendstatus());
        map.put("supplier", p.getSupplier());
        return map;
    }
}
