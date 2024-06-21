package com.micropos.orders.mapper;

import com.micropos.api.dto.OrderItemDTO;
import com.micropos.orders.model.OrderItem;

import java.time.LocalDateTime;

public class OrderItemMapper {
    public static OrderItem mapDtoToEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(orderItemDTO.getPid());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setImg(orderItemDTO.getImg());
        return orderItem;
    }
    public static OrderItemDTO mapEntityToDto(OrderItem orderItem) {
        return new OrderItemDTO(orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getImg());
    }
}
