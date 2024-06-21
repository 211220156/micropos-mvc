package com.micropos.orders.db;

import com.micropos.orders.model.Order;

import java.util.List;

public interface OrderDB {
    List<Order> getOrders();
    List<Order> getOrdersByUid(Long uid);
    String createOrder(Order order);

}
