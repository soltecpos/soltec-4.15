package com.soltec.web.repository;

import com.soltec.web.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByCategoryId(String categoryId);

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE p.isservice = false ORDER BY p.name")
    List<Product> findAllPhysicalProducts();

    @Query("SELECT p FROM Product p ORDER BY p.name")
    List<Product> findAllOrderByName();
}
