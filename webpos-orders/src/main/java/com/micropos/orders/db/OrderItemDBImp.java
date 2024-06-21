package com.micropos.orders.db;

import com.micropos.orders.jpa.OrderItemRepository;
import com.micropos.orders.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderItemDBImp implements OrderItemDB {
    private OrderItemRepository orderItemRepository;
    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
    @Override
    public List<OrderItem> getOrderItems() {
        return orderItemRepository.findAll();
    }
    @Override
    public void createItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
    @Override
    public List<OrderItem> relatedItems(String orderId) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItem orderItem : orderItemRepository.findAll()) {
            if (orderItem.getOrderId().equals(orderId)) {
                orderItemList.add(orderItem);
            }
        }
        return orderItemList;
    }
}
