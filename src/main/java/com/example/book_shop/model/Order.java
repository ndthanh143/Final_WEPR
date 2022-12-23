package com.example.book_shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderID;
    private String name;
    private String phoneNo;
    private String address;
    private int totalPrice;
    private int shippingFee = 0;
    private String payment;
    private String paymentStatus = "Chưa thanh toán";
    private String orderStatus = "Chưa xác nhận";
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_userID")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderDetail> orderDetails;
}