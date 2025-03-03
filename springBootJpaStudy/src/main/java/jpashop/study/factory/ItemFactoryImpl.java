package jpashop.study.factory;

import jpashop.study.domain.item.Album;
import jpashop.study.domain.item.Book;
import jpashop.study.domain.item.Item;
import jpashop.study.domain.item.Movie;
import jpashop.study.service.AlbumDto;
import jpashop.study.service.BookDto;
import jpashop.study.service.ItemDto;
import jpashop.study.service.MovieDto;

public class ItemFactoryImpl implements ItemFactory{
    @Override
    public Item creatItem(ItemDto itemDto) throws IllegalStateException {
        // enum 타입을 사용하면 타입 안정성을 강화할 수 있다.

        switch (itemDto.getDtype()) {
            case "Album" -> {
                AlbumDto albumDto = (AlbumDto) itemDto;
                return Album.createAlbum(albumDto.getName(), albumDto.getPrice(),
                        albumDto.getStockQuantity(), albumDto.getArtist(), albumDto.getEtc());
            }
            case "Book" -> {
                BookDto bookDto = (BookDto) itemDto;
                return Book.createBook(bookDto.getName(), bookDto.getPrice(),
                        bookDto.getStockQuantity(), bookDto.getAuthor(), bookDto.getIsbn());
            }
            case "Movie" -> {
                MovieDto movieDto = (MovieDto) itemDto;
                return Movie.createMovie(movieDto.getName(), movieDto.getPrice(),
                        movieDto.getStockQuantity(), movieDto.getDirector(), movieDto.getActor());
            }
            default -> throw new IllegalStateException("알 수 없는 데이터 타입입니다.");
        }

//        if(itemDto.getDtype().equals("Album")){
//            AlbumDto albumDto = (AlbumDto) itemDto;
//            return Album.createAlbum(albumDto.getItemName(), albumDto.getItemPrice(),
//                    albumDto.getItemStockQuantity(), albumDto.getArtist(), albumDto.getEtc());
//        } else if(itemDto.getDtype().equals("Book")) {
//            BookDto bookDto = (BookDto) itemDto;
//            return Book.createBook(bookDto.getItemName(), bookDto.getItemPrice(),
//                    bookDto.getItemStockQuantity(), bookDto.getAuthor(), bookDto.getIsbn());
//        } else if(itemDto.getDtype().equals("Movie")) {
//            MovieDto movieDto = (MovieDto) itemDto;
//            return Album.createAlbum(movieDto.getItemName(), movieDto.getItemPrice(),
//                    movieDto.getItemStockQuantity(), movieDto.getDirector(), movieDto.getActor());
//        } else {
//            throw new IllegalStateException("알 수 없는 데이터 타입입니다.");
//        }
    }
}
