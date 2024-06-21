package com.micropos.products.model;

import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@Getter
@Setter
public class Product implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "_id")
    private String _id;

    @Column(name = "price")
    private double price;

    @Column(name = "category")
    private String category;

    @Column(name = "quantity")
    private int quantity;


    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private int stock;

    @Column(name = "img")
    private String img;

    @Column(name = "average_rating")
    private double average_rating;

    @Column(name = "rating_number")
    private int rating_number;

    public Product(String id, String title, double price, String img) {
        this.id = id;
        this._id = id;
        this.price = price;
        this.img = img;
        this.stock = 1;
        this.quantity = new Random().nextInt(20);
        this.name = title;
    }
    @Override
    public String toString() {
        return getId() + "\t" + get_id() + "\t" + getPrice() + "\t" + getCategory() + "\t" + getQuantity() + "\t" + getName() + "\t" + getStock() + "\t" + getImg();
    }
}
