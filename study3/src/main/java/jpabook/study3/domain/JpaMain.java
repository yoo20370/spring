package jpabook.study3.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction ts = em.getTransaction();

        ts.begin();
        try {
            Member member = new Member();
            member.setName("유영우");
            member.setAddress(new Address("수원시", "498", "123"));

            Order order = new Order();
            order.setOrderStatus(OrderStatus.WAIT);
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            delivery.setDeliveryStatus(DeliveryStatus.WAIT);
            order.setDelivery(delivery);

            order.setMemberInfo(member);
            member.getOrders().add(order);

            Item item1 = new Book();
            item1.setName("스프링의 정석");
            item1.setPrice(19000);

            Item item2 = new Book();
            item2.setName("JPA");
            item2.setPrice(32000);

            em.persist(item1);
            em.persist(item2);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderInfo(order);
            orderItem.setItemInfo(item1);

            OrderItem orderItem1 = new OrderItem();
            orderItem1.setOrderInfo(order);
            orderItem1.setItemInfo(item2);

            em.persist(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            em.remove(findMember);


            ts.commit();
        } catch (Exception e) {
            ts.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
