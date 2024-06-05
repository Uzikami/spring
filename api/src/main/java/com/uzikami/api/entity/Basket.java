package com.uzikami.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="basket")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyer_id")
    private String buyerId;
    public Basket(String buyerId) {
        this.buyerId = buyerId;
    }

}

