package jpabook.jpashop.entity.item;

import jakarta.persistence.*;
import jpabook.jpashop.entity.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 변경해야 할 일이 있으면 setter를 이용해서 변경할 것이 아니라 (밖에서 계산해서 넣는게 아니라)
    // 핵심 비즈니스 메서드를 엔티티 내에 생성해서 변경하고 validation 수행

    /**
     * 도메인 주도 설계할 때, 엔티티 자체가 해결할 수 있는 것들은 엔티티 안에 비즈니스 로직을 넣는게 좋다.
     * 객체 지향 적인 설계 (엔티티 내부에 있어야 응집도가 높다.)
     * 개발할 때, Item.getStockQuantity()로 가져와서 값을 더해서 Item.setStockQuantity()로 값을 설정했을 것
     * 객체지향적으로 생각해보면 데이터를 가지고 있는 쪽에 비즈니스 로직을 작성하는게 좋다. -> 응집력이 올라감
     * 그래서 Item 엔티티에 stockQuantity 값을 수정하는 비즈니스 로직 작성
     */
    // === 비즈니스 로직 === //
    /**
     * 재고수량 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    public Item() {

    }


}
