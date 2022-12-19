package com.example.book_shop.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private Integer productID;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private String description;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "fk_categoryID")
    private Category category;


}
