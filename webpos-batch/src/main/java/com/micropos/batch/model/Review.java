package com.micropos.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
@Getter
@Setter
//@SQLInsert(sql = "INSERT IGNORE INTO review(parent_asin, rating, text, title, user_id, timestamp) " +
//        "VALUES (?, ?, ?, ?, ?, ?)" )
public class Review implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//让数据库自己生成自增id。不在应用端指定id，jpa就不用判断是否重复
    private Integer id;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "rating")
    private double rating;

    @Column(name = "title", length = 2000)
    private String title;

    @Column(name = "text", length = 20000)
    private String text;

    @Column(name = "parent_asin")
    private String parent_asin;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
    @Column(name = "user_id")
    private String user_id;

    @ElementCollection
    @Column(name = "images", columnDefinition = "json")
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
