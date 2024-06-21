package com.micropos.delivery.jpa;

import com.micropos.delivery.model.Delivery;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@EntityScan("com.micropos.delivery.model.delivery")
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
}
