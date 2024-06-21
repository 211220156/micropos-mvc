package com.micropos.batch.jpa;

import com.micropos.batch.model.AmazonProduct;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@EntityScan("com.micropos.batch.model.AmazonProduct")
public interface ProductRepository extends JpaRepository<AmazonProduct, Integer> {
}
