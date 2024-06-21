package com.micropos.products.jpa;

import com.micropos.products.model.Product;
//import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
@Component
@EntityScan("com.micropos.products.model.Product")
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.quantity = :newQuantity WHERE p.id = :productId")
    void updateProductQuantity(@Param("productId") String productId, @Param("newQuantity") Integer newQuantity);
    @Transactional
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
    List<Product> searchByName(@Param("keyword") String keyword);
}
