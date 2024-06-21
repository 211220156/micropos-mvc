package com.micropos.delivery.biz;

import com.micropos.delivery.model.Delivery;

import java.util.List;

public interface DeliveryService {
    List<Delivery> getDeliveries();
    List<Delivery> getDeliveriesByUserId(Long uid);
    void createDelivery(Delivery delivery);
}
