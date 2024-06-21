package com.micropos.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "amazon_product")
@Getter
@Setter
public class AmazonProduct implements Serializable {
    @Id
    @Column(name = "parent_asin")
    private String parent_asin;

    @Column(name = "average_rating")
    private double average_rating;

    @Column(name = "rating_number")
    private int rating_number;

    @Column(name = "main_category")
    private String main_category;

    @Column(name = "title", length = 3000)
    private String title;

    @Column(name = "price")
    private double price;

    @ElementCollection
    @Column(name = "categories")
    private List<String> categories;

    @ElementCollection
    @Column(name = "images", columnDefinition = "json")
    private List<Image> images;

    @Column(name = "quantity")
    private int quantity;

    @Override
    public String toString() {
        return getParent_asin() + "\t" + getAverage_rating() + "\t" + getRating_number() + "\t" + getMain_category() + "\t" + getTitle() + "\t" + getPrice() + "\t" + getCategories() + "\t" + getImages() + "\t" + getQuantity();
    }

    public String getImages() {
        if (images.isEmpty()) return null;
        return images.get(0).getLarge();
    }

}
