package jpashop.study.service;


import jakarta.persistence.EntityManager;
import jpashop.study.domain.item.Album;
import jpashop.study.domain.item.Book;
import jpashop.study.domain.item.Item;
import jpashop.study.domain.item.Movie;
import jpashop.study.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(readOnly = true)
public class ItemServiceImplTest {

    @Autowired
    ItemService itemService;

    @Autowired
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @Transactional
    public void 아이템_저장_및_조회() throws Exception {
        //given
        AlbumDto albumDto = createAlbumDto();
        BookDto bookDto = createBookDto();
        MovieDto movieDto = createMovieDto();
        AlbumDto missingType = new AlbumDto();
        // 무작위 타입
        missingType.setDtype("aaaa");

        //when
        itemService.saveItem(albumDto);
        itemService.saveItem(bookDto);
        itemService.saveItem(movieDto);
        Item findItem1 = itemService.findOne(1L);
        Item findItem2 = itemService.findOne(2L);
        Item findItem3 = itemService.findOne(3L);

        //then
        // 팩토리 클래스 잘 동작하는지 확인
        Assertions.assertThat(findItem1).isInstanceOf(Album.class);
        Assertions.assertThat(findItem2).isInstanceOf(Book.class);
        Assertions.assertThat(findItem3).isInstanceOf(Movie.class);
        assertThrows(IllegalStateException.class, () -> itemService.saveItem(missingType));
    }

    @Test
    @Transactional
    public void 모두_불러오기() throws Exception {
        //given
        AlbumDto albumDto = createAlbumDto();
        BookDto bookDto = createBookDto();
        MovieDto movieDto = createMovieDto();

        //when
        itemService.saveItem(albumDto);
        itemService.saveItem(bookDto);
        itemService.saveItem(movieDto);

        //then
        Assert.assertEquals(3, itemService.findItems().size());
    }

    @Test
    @Transactional // 항상 체크할 것
    // TODO: 현재 문제점 - 수정한 데이터만 변경감지를 수행해야하는데 모두 변경되고 있음 - 전체를 업데이트하는 쿼리가 나가고 있음, 가장 베스트는 변경된 것만 변경하는 것
    public void 아이템_정보_업데이트() throws Exception {
        //given
        AlbumDto albumDto = createAlbumDto();
        albumDto.setItemId(1L);

        itemService.saveItem(albumDto);

        // 영속성 컨텍스트 비움
        em.flush();
        em.clear();


        //when
        Item findItem = em.find(Item.class, albumDto.getItemId());
        albumDto.setItemName("DM");
        findItem.updateItem(albumDto);

        // 영속성 컨텍스트 비움
        // 글 수정 페이지에서 그대로 데이터를 가져왔을 때, 영속성 컨텍스트는 해당 글과 같은 Id 값을 갖는 엔티티를 관리하지 않는 상태(준영속)
        em.flush();
        em.clear();

        Item findItem2 = em.find(Item.class, albumDto.getItemId());
        //then

        System.out.println(findItem2.getName());
        System.out.println(albumDto.getItemName());
        Assert.assertEquals(findItem2.getName(), albumDto.getItemName());
    }

    public AlbumDto createAlbumDto() {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setDtype("Album");
        albumDto.setItemName("슈퍼소닉");
        albumDto.setItemPrice(19000);
        albumDto.setItemStockQuantity(2500);
        albumDto.setArtist("백지헌");
        albumDto.setEtc("etc");

        return albumDto;
    }

    public BookDto createBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setDtype("Book");
        bookDto.setItemName("김영한 JPA");
        bookDto.setItemPrice(30000);
        bookDto.setItemStockQuantity(20000);
        bookDto.setAuthor("김영한");
        bookDto.setIsbn("123-123");

        return bookDto;
    }

    public MovieDto createMovieDto() {
        MovieDto movieDto = new MovieDto();
        movieDto.setDtype("Movie");
        movieDto.setItemName("너의이름은");
        movieDto.setItemPrice(12550);
        movieDto.setItemStockQuantity(1000);
        movieDto.setDirector("신카이 마코토");
        movieDto.setActor("다수");

        return movieDto;
    }

}