package com.micropos.orders.biz;

import com.micropos.orders.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> orders();

    List<Order> getOrdersByUid(Long uid);

    String createOrder(Order order);

}
