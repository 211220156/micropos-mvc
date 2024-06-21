package com.micropos.orders.jpa;

import com.micropos.orders.model.OrderItem;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@EntityScan("com.micropos.orders.model.OrderItem")
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
