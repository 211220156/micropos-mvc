//package com.micropos.products.jpa;
//
//import com.micropos.products.model.Amazon.AmazonProduct;
//import jakarta.transaction.Transactional;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@Component
//@EntityScan("com.micropos.products.model.Amazon.*")
//public interface AmazonRepository extends JpaRepository<AmazonProduct, Integer> {
//    @Transactional
//    @Modifying
//    @Query("UPDATE AmazonProduct p SET p.quantity = :newQuantity WHERE p.parent_asin = :productId")
//    void updateProductQuantity(@Param("productId") String productId, @Param("newQuantity") Integer newQuantity);
//}
