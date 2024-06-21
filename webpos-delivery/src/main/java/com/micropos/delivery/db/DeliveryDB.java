package com.micropos.delivery.db;

import com.micropos.delivery.model.Delivery;

import java.util.List;

public interface DeliveryDB {
    List<Delivery> getDeliveries();
    List<Delivery> getDeliveriesByUserId(Long uid);
    void createDelivery(Delivery delivery);
}
