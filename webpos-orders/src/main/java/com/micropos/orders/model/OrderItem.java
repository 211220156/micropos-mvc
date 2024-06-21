package com.micropos.orders.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderItem")
@Getter
public class OrderItem {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "orderId")
    private String orderId;
    @Column(name = "productId")
    private String productId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;
    @Column
    private String img;
}
