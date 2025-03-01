package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.entity.OrderStatus;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

// 실제 테스트는 의존성 없이 순수하게 메서드를 단위 테스트하는게 좋다.
// 현재는 DB, 스프링 부트가 엮여서 실행됨

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;



    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember("회원1");

        Item book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId =  orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(orderId);

        // 메시지, 기대값, 실제값
        Assert.assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, findOrder.getOrderStatus());
        Assert.assertEquals("주문한 상품 수가 정확해야 한다.", 1, findOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 * 수량이다.", book.getPrice() * orderCount, findOrder.getTotalPrice());
        Assert.assertEquals("주문 수량만큼 재고가 줄어야 한다.", 10 - orderCount, book.getStockQuantity());
    }

    


    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember("회원1");
        Item book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("주문 취소 시 주문 상태가 cancel이 되어야 한다.", OrderStatus.CANCEL, getOrder.getOrderStatus());
        Assert.assertEquals("처음으로 설정한 수량과 복구된 수량이 동일해야 한다.", 10, book.getStockQuantity());

    }
    // 통합 테스트도 좋지만, 단위 테스트가 중요하다.
    // addStock, removeStock 등등
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember("회원1");
        Item book = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;
        
        //when
        Long getId = orderService.order(member.getId(), book.getId(), orderCount);
        
        //then
        Assert.fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);

        em.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "강가", "123-123"));

        // 테스트이기 때문에 단순하게 persist()하는게 좋다. (?)
        em.persist(member);
        return member;
    }

    // 테스트는 단위 테스트하는게 좋다.
    // 외부 시스템이 전혀 붙지 않는 상태에서 하는 것이 좋다.
    // 도메인 모델 패턴을 사용하면, 리포지토리에 관계 없이 엔티티 로직은 테스트를 작성할 수 있다.
    // 통합 테스트는 전체적으로 잘 돌아가는지 확인할 때 의미가 있다.
}