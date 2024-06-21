package com.micropos.products.model.Amazon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review implements Serializable{
    private Integer id;
    private String timestamp;
    private double rating;
    private String title;
    private String text;
    private String parent_asin;
    private String user_id;
    private List<Image> images;

    @Override
    public String toString() {
        return getTimestamp() + "\t" + getTitle() + "\t" + getText() + "\t" + getParent_asin() + "\t" + getUser_id() + "\t" + getImages();
    }

    public String getImages() {
        if (images.isEmpty()) return null;
        return images.get(0).getLarge();
    }
}
