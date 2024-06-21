package com.micropos.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
    private String pid;
    private int quantity;
    private double price;
    private String img;
}
