package jpabook.jpashoop.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    private String name;


    private String city;


    private String street;


    private String zipcode;

    public Member() { }


}
