package com.micropos.delivery.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Delivery")
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "orderId")
    private String orderId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "carrier")
    private String carrier;

    @Column(name = "tracking_num")
    private String tracking_num;

    @Column(name = "status")
    private String status;
}
