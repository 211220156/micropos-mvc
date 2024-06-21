package com.micropos.delivery.rest;

import com.micropos.api.utils.UserContext;
import com.micropos.delivery.biz.DeliveryService;
import com.micropos.delivery.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@RestController
public class DeliveryController {
    private DeliveryService deliveryService;
    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

//    @GetMapping("/delivery")
//    public ResponseEntity<List<Delivery>> getDeliveries() {
//        return new ResponseEntity<>(deliveryService.getDeliveries(), HttpStatus.OK);
//    }

    @GetMapping("/delivery")
    public ResponseEntity<List<Delivery>> getDeliveriesByUserId() {
        return new ResponseEntity<>(deliveryService.getDeliveriesByUserId(UserContext.getUser()), HttpStatus.OK);
    }

    @Bean
    Consumer<String> createDelivery() {
        return str -> {
            Delivery delivery = new Delivery();
            String[] strings = str.split("\\|");
            assert(strings.length == 2);
            delivery.setOrderId(strings[0]);
            delivery.setUserId(Long.valueOf(strings[1]));
            delivery.setStatus("运送中");
            delivery.setCarrier("顺丰");
            delivery.setTracking_num(UUID.randomUUID().toString());

            deliveryService.createDelivery(delivery);
        };
    }

}
