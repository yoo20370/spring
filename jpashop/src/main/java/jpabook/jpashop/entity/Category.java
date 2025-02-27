package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "category_item", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "category_id"),    // 중간 테이블에 있는 카테고리 쪽의 외래키
            inverseJoinColumns = @JoinColumn(name = "item_id")  // 아이템 쪽의 외래키
    ) // 여기서 category_id는 중간 테이블에서 있는 카테고리 아이디(외래키)
    private List<Item> items = new ArrayList<>();

//    @OneToMany(mappedBy = "category")
//    private List<CategoryItem> categoryItems = new ArrayList<>();
    public Category() {

    }

    // 연관관계 편의 메서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
