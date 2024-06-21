package com.micropos.orders.mapper;

import com.micropos.api.dto.OrderDTO;
import com.micropos.orders.model.Order;
import com.micropos.orders.model.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public class OrderMapper {
    public static Order mapDtoToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUid(orderDTO.getUid());
        order.setAmount(orderDTO.getAmount());
        order.setStatus(orderDTO.getStatus());
        order.setOrderTime(LocalDateTime.now());
        return order;
    }
    public static OrderDTO mapEntityToDto(Order order, List<OrderItem> orderItemList) {
        return new OrderDTO(order.getUid(), order.getAmount(),
                orderItemList.stream().map(OrderItemMapper::mapEntityToDto).toList(), order.getStatus());
    }
}
