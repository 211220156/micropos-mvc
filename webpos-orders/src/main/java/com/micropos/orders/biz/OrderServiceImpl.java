package com.micropos.orders.biz;

import com.micropos.orders.db.OrderDB;
import com.micropos.orders.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class OrderServiceImpl implements OrderService {
    private OrderDB orderDB;
    @Autowired
    public void setOrderDB(OrderDB orderDB) {
        this.orderDB = orderDB;
    }
    @Override
    public List<Order> orders() {
        return orderDB.getOrders();
    }
    @Override
    public String createOrder(Order order) {
        return orderDB.createOrder(order);
    }
    @Override
    public List<Order> getOrdersByUid(Long uid) {
        return orderDB.getOrdersByUid(uid);
    }
}
