package jpashop.study.domain.item;

import jakarta.persistence.*;
import jpashop.study.exception.NotEnoughStockException;
import jpashop.study.repository.ItemRepository;
import jpashop.study.service.AlbumDto;
import jpashop.study.service.BookDto;
import jpashop.study.service.ItemDto;
import jpashop.study.service.MovieDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    protected Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // 필드 변경 메서드
    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    //== 비즈니스로직 ==//
    /**
     * 수량 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 수량 감소
     */
    public void removeStock(int quantity) throws NotEnoughStockException {
        int result = this.stockQuantity - quantity;
        if(result < 0) throw new NotEnoughStockException("주문 수량이 재고보다 많습니다.");

        this.stockQuantity = result;
    }

    /**
     * 아이템의 정보 수정 비즈니스 로직
     */
    public abstract void updateItem(ItemDto itemDto);

}
