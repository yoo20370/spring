package jpashop.study.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    protected Delivery(Address address) {
        this.address = address;
        this.status = DeliveryStatus.DELIVERY;
    }

    public static Delivery createDelivery(Address address) {
        return new Delivery(address);
    }

    public void changeOrder(Order order) {
        this.order = order;
    }
}
