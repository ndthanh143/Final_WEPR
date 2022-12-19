package com.example.book_shop.model;

import javax.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Integer cartID;
    private int totalItems;
    private int totalPrices;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_userID", referencedColumnName = "userid")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItem> cartItem;

}
