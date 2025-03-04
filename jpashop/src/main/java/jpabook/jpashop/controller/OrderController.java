package jpabook.jpashop.controller;

import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Order;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    // 고객, 아이템을 선택할 수 있어야 하므로 여러 연관관계 필요
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        // 컨트롤러에서 엔티티를 모두 찾는 것보다 현재가 더 나은 이유
        // 컨트롤러 로직도 지져분해지는 것도 있지만
        // 서비스 게층에서 찾는 것이 할 수 있는게 더 많다.
        // 엔티티도 영속상태로 흘러가므로 문제가 훨씬 깔끔하게 해결된다.
        // 커맨드성은 주로 컨트롤러에서 서비스로 식별자만 넘기고 실제 비즈니스 서비스에서
        // 엔티티를 찾는 것부터 모두 수행
        // 엔티티 값들도 트랜잭션안에서 가져와야 영속상태가 되어 진행됨(영속성 컨텍스트 존재)
        // 상태 변경도 가능하는 등 기능 사용 가능
        // 만약 컨트롤러에서 조회해서 객체를 넘기면 해당 객체는 영속성 컨텍스트와 관련없는 객체가 됨
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    // 단순 조회의 경우
    // 단순하게 위임, 컨트롤러에서 바로 호출해도 상관없음
    // 아키텍처를 어떻게 잡느냐에 따라 다르다.
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        // @ModelAttribute를 사용하면 OrderSearch가 자동으로 model에 담긴다.
        List<Order> orders = orderService.findOrders(orderSearch);
        for (Order o : orders) {
            System.out.println("o = " + o);
        }
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
