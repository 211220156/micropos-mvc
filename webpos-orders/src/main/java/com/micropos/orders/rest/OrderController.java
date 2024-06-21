package com.micropos.orders.rest;

import com.micropos.api.dto.OrderDTO;
import com.micropos.api.dto.OrderItemDTO;
import com.micropos.api.utils.UserContext;
import com.micropos.orders.biz.OrderItemService;
import com.micropos.orders.biz.OrderService;
import com.micropos.orders.mapper.OrderItemMapper;
import com.micropos.orders.mapper.OrderMapper;
import com.micropos.orders.model.Order;
import com.micropos.orders.model.OrderItem;
import jakarta.annotation.Resource;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
    @Resource
    private StreamBridge streamBridge;
    private static int orderId = 1;
    private static int orderItemId = 1;
    private OrderService orderService;
    private OrderItemService orderItemService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> listOrders() {
        System.out.println("in list Order");
        return new ResponseEntity<>(
                orderService.getOrdersByUid(UserContext.getUser()),
                HttpStatus.OK
        );
    }

    @GetMapping("/orders/detail")
    public ResponseEntity<List<OrderDTO>> listOrdersDetail() {
        System.out.println("in list order detail");
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderService.getOrdersByUid(UserContext.getUser())) {
            orderDTOList.add(OrderMapper.mapEntityToDto(order, orderItemService.relatedItems(order.getId())));
        }
        return new ResponseEntity<>(
                orderDTOList,
                HttpStatus.OK
        );
    }

    @GetMapping("/orderItems")
    public ResponseEntity<List<OrderItem>> listOrderItems() {
        System.out.println("in list OrderItems");
        return new ResponseEntity<>(
                orderItemService.orderItems(),
                HttpStatus.OK
        );
    }

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody OrderDTO orderDTO) {
        System.out.println("in create Order!");
        Order order = OrderMapper.mapDtoToEntity(orderDTO);
        order.setId(String.valueOf(orderId));
        String ret = orderService.createOrder(order);

        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            OrderItem orderItem = OrderItemMapper.mapDtoToEntity(orderItemDTO);
            orderItem.setOrderId(String.valueOf(orderId));
            orderItem.setId(String.valueOf(orderItemId++));
            orderItemService.createItem(orderItem);
        }
        //需要向mq发送信息，通知产生物流
        try {
            streamBridge.send("orderToDelivery", orderId + "|" + order.getUid().toString());
        } catch (Exception ignored) {
            System.out.println("exception ignored.");
        }

        orderId++;
        return ret;
    }
}
