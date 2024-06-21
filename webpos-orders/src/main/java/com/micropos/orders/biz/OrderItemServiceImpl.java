package com.micropos.orders.biz;

import com.micropos.orders.db.OrderItemDB;
import com.micropos.orders.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemDB orderItemDB;
    @Autowired
    public void setOrderItemDB(OrderItemDB orderItemDB) {
        this.orderItemDB = orderItemDB;
    }
    @Override
    public List<OrderItem> orderItems() {
        return orderItemDB.getOrderItems();
    }
    @Override
    public void createItem(OrderItem orderItem) {
        orderItemDB.createItem(orderItem);
    }
    @Override
    public List<OrderItem> relatedItems(String orderId) {
        return orderItemDB.relatedItems(orderId);
    }
}
