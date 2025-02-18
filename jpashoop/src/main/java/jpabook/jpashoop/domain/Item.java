package jpabook.jpashoop.domain;

import jakarta.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="ITEM_ID")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    public Item() {}

    public Item(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
