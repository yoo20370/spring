package jpashop.study.domain.item;

import jakarta.persistence.Entity;
import jpashop.study.service.AlbumDto;
import jpashop.study.service.ItemDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item{
    private String artist;
    private String etc;

    public void changeArtist(String artist) {
        this.artist = artist;
    }

    protected Album(String name, int price, int stockQuantity, String artist, String etc) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }

    public static Album createAlbum(String name, int price, int stockQuantity, String artist, String etc) {
        return new Album(name, price, stockQuantity, artist, etc);
    }

    @Override
    public void updateItem(ItemDto itemDto) {
        AlbumDto albumDto = (AlbumDto) itemDto;
        this.changeName(albumDto.getName());
        this.changePrice(albumDto.getPrice());
        this.changeStockQuantity(albumDto.getStockQuantity());
        this.changeArtist(albumDto.getArtist());
    }
}
