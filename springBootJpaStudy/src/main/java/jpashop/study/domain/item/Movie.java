package jpashop.study.domain.item;

import jakarta.persistence.Entity;
import jpashop.study.service.ItemDto;
import jpashop.study.service.MovieDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends Item{
    private String director;
    private String actor;

    public void changeDirector(String director) {
        this.director = director;
    }

    public void changeActor(String actor) {
        this.actor = actor;
    }

    protected Movie(String name, int price, int stockQuantity, String director, String actor) {
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }

    public static Movie createMovie(String name, int price, int stockQuantity, String director, String actor) {
        return new Movie(name, price, stockQuantity, director, actor);
    }

    @Override
    public void updateItem(ItemDto itemDto) {
        MovieDto movieDto = (MovieDto) itemDto;
        this.changeName(movieDto.getName());
        this.changePrice(movieDto.getPrice());
        this.changeStockQuantity(movieDto.getStockQuantity());
        this.changeDirector(movieDto.getDirector());
        this.changeActor(movieDto.getActor());
    }
}
