package jpashop.study.service;

import jpashop.study.domain.*;
import jpashop.study.domain.item.Album;
import jpashop.study.domain.item.Item;
import jpashop.study.exception.NotEnoughStockException;
import jpashop.study.repository.ItemRepository;
import jpashop.study.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 주문_및_조회_테스트() throws Exception {
        //given
        Member member = Member.createMember("유영우", new Address("수원", "영통로", "16708"));
        Long savedMemberId = memberRepository.save(member);

        Item item = Album.createAlbum("좋은날", 19000, 100, "아이유", "etc");
        Long savedItemId = itemRepository.save(item);

        //when
        Long savedOrderId = orderService.order(savedMemberId, savedItemId, 10);


        //then
        Order findOrder = orderService.findOne(savedOrderId);

        Assert.assertEquals(member, findOrder.getMember());
        Assert.assertEquals(OrderStatus.ORDER, findOrder.getStatus());
        Assert.assertEquals(item, findOrder.getOrderItems().get(0).getItem());
        Assert.assertEquals(10, findOrder.getOrderItems().get(0).getCount());
        Assert.assertEquals(10 * item.getPrice(), findOrder.getOrderItems().get(0).getOrderItemsToTalPrice());
    }
    @Test(expected = NotEnoughStockException.class)
    public void 재고_수량_테스트() throws Exception {
        //given
        Member member = Member.createMember("유영우", new Address("수원", "영통로", "16708"));
        Long savedMemberId = memberRepository.save(member);

        Item item = Album.createAlbum("좋은날", 19000, 100, "아이유", "etc");
        Long savedItemId = itemRepository.save(item);

        //when
        Long savedOrderId = orderService.order(savedMemberId, savedItemId, 110);

        //then
        Assert.fail("예외가 발생하지 않았습니다.");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = Member.createMember("유영우", new Address("수원", "영통로", "16708"));
        Long savedMemberId = memberRepository.save(member);

        Item item = Album.createAlbum("좋은날", 19000, 100, "아이유", "etc");
        Long savedItemId = itemRepository.save(item);
        //when
        Long savedOrderId = orderService.order(savedMemberId, savedItemId, 10);

        orderService.cancelOrder(savedOrderId);
        //then
        Order findOrder = orderService.findOne(savedOrderId);

        Assert.assertEquals(findOrder.getStatus(), OrderStatus.CANCEL);
        Assert.assertEquals(100, item.getStockQuantity());
    }
}