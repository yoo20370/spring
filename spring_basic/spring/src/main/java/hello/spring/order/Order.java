package hello.spring.order;

public class Order {

    private String itemName;
    private Long memberId;
    private Integer price;
    private Integer discountPrice;

    public Order(String itemName, Long memberId, Integer price, Integer discountPrice) {
        this.itemName = itemName;
        this.memberId = memberId;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "itemName='" + itemName + '\'' +
                ", memberId=" + memberId +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
