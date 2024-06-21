package com.micropos.api.dto;

import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long uid;
    private double amount;
    private List<OrderItemDTO> items;
    private String status;
}
