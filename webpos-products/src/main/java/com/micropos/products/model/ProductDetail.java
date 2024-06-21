package com.micropos.products.model;

import com.micropos.products.model.Amazon.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetail {
    private Product product;
    private List<Review> reviewList;
}
