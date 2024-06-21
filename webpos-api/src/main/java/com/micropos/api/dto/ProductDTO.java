package com.micropos.api.dto;


import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private String id;
    private String _id;
    private String price;
    private String category;
    private int quantity;
    private String name;
    private int stock;
    private String img;
    private double average_rating;
    private int rating_number;
}
