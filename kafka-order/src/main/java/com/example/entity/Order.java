package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private int orderId;

    @Column(nullable = false, length = 20)
    private String orderNumber;

    @Column(nullable = false, length = 200)
    private String orderLocation;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    @Column(nullable = false, length = 20)
    private String creditCardNumber;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

}
