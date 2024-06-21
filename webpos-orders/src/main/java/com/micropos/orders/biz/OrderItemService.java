package com.micropos.orders.biz;

import com.micropos.orders.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> orderItems();
    List<OrderItem> relatedItems(String orderId);

    void createItem(OrderItem orderItem);
}
