package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 연관관계 주인이 누구인지 지정
    // Orders의 member 필드가 연관관계 주인이므로 memmber로 지정
    // 컬렉션에 값이 삽입되어도 값이 변경되지 않는다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Member() {

    }


}
