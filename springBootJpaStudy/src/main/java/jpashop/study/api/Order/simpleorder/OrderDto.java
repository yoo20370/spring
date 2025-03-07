package jpashop.study.api.Order.simpleorder;

import jpashop.study.api.Order.OrderItemDto;
import jpashop.study.domain.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Data
//public class OrderDto {
//    private Member member;
//    private Delivery delivery;
//    private LocalDateTime orderDate;
//    private OrderStatus status;
//    private List<OrderItem> orderItems;
//
//    public OrderDto(Member member, Delivery delivery, LocalDateTime orderDate, OrderStatus status, List<OrderItem> orderItems) {
//        this.member = member;
//        this.delivery = delivery;
//        this.orderDate = orderDate;
//        this.status = status;
//        this.orderItems = orderItems;
//    }
//}


@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;


    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems().stream()
                .map(o -> new OrderItemDto(o))
                .collect(Collectors.toList());
    }

    public OrderDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}