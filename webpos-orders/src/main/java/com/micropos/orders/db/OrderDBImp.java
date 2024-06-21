package com.micropos.orders.db;

import com.micropos.orders.jpa.OrderRepository;
import com.micropos.orders.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class OrderDBImp implements OrderDB {
    private OrderRepository orderRepository;
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    @Override
    public String createOrder(Order order) {
        orderRepository.save(order);
        return "create order succeed!";
    }
    @Override
    public List<Order> getOrdersByUid(Long uid) {
        List<Order> ret = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            if (order.getUid().equals(uid)) {
                ret.add(order);
            }
        }
        return ret;
    }
}
