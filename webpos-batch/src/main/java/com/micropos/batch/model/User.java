package com.micropos.batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

//@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "amazon_user")
@Getter
@Setter
public class User implements Serializable/*, Persistable<String>*/ {//实现了Persistable<String>接口，是为了设置isNew为true。这样jpa就会认为对象是新的。否则就会执行merge方法，去数据库中select查看是否已存在。
    /*@Transient
    private boolean isNew = true;
    @Override
    public String getId() {
        return user_id;
    }
    @Override
    public boolean isNew() {
        return isNew;
    }*/
    @Id
    @Column(name = "user_id")
    private String user_id;

//    public User(String user_id) {
//        this.user_id = user_id;
//    }
}
