package com.micropos.delivery.biz;

import com.micropos.delivery.db.DeliveryDB;
import com.micropos.delivery.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeliveryServiceImpl implements DeliveryService {
    private DeliveryDB deliveryDB;
    @Autowired
    public void setDeliveryDB(DeliveryDB deliveryDB) {
        this.deliveryDB = deliveryDB;
    }
    @Override
    public List<Delivery> getDeliveries() {
        return deliveryDB.getDeliveries();
    }
    @Override
    public List<Delivery> getDeliveriesByUserId(Long uid) {
        return deliveryDB.getDeliveriesByUserId(uid);
    }
    @Override
    public void createDelivery(Delivery delivery) {
        deliveryDB.createDelivery(delivery);
    }

}
