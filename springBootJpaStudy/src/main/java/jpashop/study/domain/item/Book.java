package jpashop.study.domain.item;

import jakarta.persistence.Entity;
import jpashop.study.service.BookDto;
import jpashop.study.service.ItemDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{
    private String author;
    private String isbn;

    public void changeAuthor(String author) {
        this.author = author;
    }

    protected Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        return new Book(name, price, stockQuantity, author, isbn);
    }

    @Override
    public void updateItem(ItemDto itemDto) {
        BookDto bookDto = (BookDto) itemDto;
        this.changeName(bookDto.getItemName());
        this.changePrice(bookDto.getItemPrice());
        this.changeStockQuantity(bookDto.getItemStockQuantity());
        this.changeAuthor(bookDto.getAuthor());
    }
}
