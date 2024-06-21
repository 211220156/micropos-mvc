package com.micropos.delivery.db;

import com.micropos.delivery.jpa.DeliveryRepository;
import com.micropos.delivery.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryDBImpl implements DeliveryDB {
    private DeliveryRepository deliveryRepository;
    @Autowired
    public void setDeliveryRepository(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }
    @Override
    public List<Delivery> getDeliveries() {
        return deliveryRepository.findAll();
    }
    @Override
    public List<Delivery> getDeliveriesByUserId(Long uid) {
       List<Delivery> ret = new ArrayList<>();
       for (Delivery delivery : deliveryRepository.findAll()) {
           if (delivery.getUserId().equals(uid)) {
               ret.add(delivery);
           }
       }
       return ret;
    }
    @Override
    public void createDelivery(Delivery delivery) {
        deliveryRepository.save(delivery);
    }
}
