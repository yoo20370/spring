package jpashop.study.api.Order.simpleorder;

import jpashop.study.api.Result;
import jpashop.study.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderSimpleFacade orderSimpleFacade;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        return orderSimpleFacade.simpleOrdersV1();
    }

    @GetMapping("/api/v2/simple-orders")
    public Result<List<OrderDto>> simpleOrdersV2() {
        return orderSimpleFacade.simpleOrdersV2();
    }

    @GetMapping("/api/v3/simple-orders")
    public Result<List<OrderDto>> simpleOrdersV3() {
        return orderSimpleFacade.simpleOrdersV3();
    }

    @GetMapping("/api/v4/simple-orders")
    public Result<List<OrderDto>> simpleOrderV4() {
        return orderSimpleFacade.simpleOrdersV4();
    }
}
