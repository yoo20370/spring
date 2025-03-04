package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

//    public List<Order> findOrderByName(String userName) {
//        return em.createQuery("select o from Order o join o.member m where m.name = :userName", Order.class)
//                .setParameter("userName", userName)
//                .getResultList();
//    }

    // 동적 쿼리를 사용해서 검색 기능을 제작할 것
    // 문자열로 동적 쿼리를 작성하는 것은 너무 힘들고, 실수할 가능성이 매우 높다. 버그 잡기도 힘들다.
//    public List<Order> findAllByString(OrderSearch orderSearch) {
//        String jpql = "select o from Order o join o.member m";
//
//        // flag 변수라고 생각
//        boolean isFirstCondition = true;
//
//        // 주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " o.status = :status";
//        }
//
//        //회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " m.name like :name";
//        }
//
//        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
//                .setMaxResults(1000); //최대 1000건
//        if (orderSearch.getOrderStatus() != null) {
//            query = query.setParameter("status", orderSearch.getOrderStatus());
//        }
//
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            query = query.setParameter("name", orderSearch.getMemberName());
//        }
//        return query.getResultList();
//
////        return em.createQuery("select o from Order o join o.member m "
////                        + " where o.orderStatus =:status "
////                        + " and m.name like :name", Order.class)
////                .setParameter("status", orderSearch.getOrderStatus())
////                .setParameter( "name", orderSearch.getMemberName())
////                .setMaxResults(1000) // 최대 1000건
////                .getResultList();
//    }
    public List<Order> findAllByString(OrderSearch orderSearch) {
//language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
//주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
//회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }
    /**
     * JPA Criteria
     * JPA에서 동적 쿼리를 자바로 작성할 수 있게 해주는 표준
     * JPQL을 자바 코드로 작성할 수 있게 만든 것
     * 동적 쿼리 작성시 좋음
     * 치명적인 단점이 있다. -> 유지보수성 제로 -> 코드를 보고 SQL 쿼리 예측 매우 어려움
     * 영한님이 권장하는 방법이 아니다.
     * QueryDSL을 사용하면 된다.
     * 실무 - 스프링부트, JPA, Spring Data JPA, QueryDSL 이렇게는 반드시 가져간다.
     */
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);       // 응답 타입 설정
        Root<Order> o = cq.from(Order.class);                        // 별칭을 잡는다.
        Join<Order, Member> m = o.join("member", JoinType.INNER); // 회원과 조인
        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),  // Predicate 자체가 조건
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName()
                            + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000 건
        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery("select o from Order o join fetch o.member join fetch o.delivery", Order.class)
                .getResultList();
    }



//    public List<Order> findAll(OrderSearch orderSearch) {
//        Qorder order = QOrder.order;
//        QMember member = QMember.member;
//
//        return query
//                .select(order)
//                .from(order)
//                .join(order.member, member)
//                .where(statusEq(orderSearch.getOrderStatus())),
//                        nameLike(orderSearch.getMemberName())
//                .limit(1000)
//                .fetch();
//    }
//
//    private BooleanExpression statusEq(OrderStatus statusCond){
//        if(statusCond == null) {
//            return null;
//        }
//        return order.status.eq(statuscond);
//    }
//
//    private BooleanExpression nameLike(String nameCond){
//        if(!StringUtils.hasText(nameCond)){
//            return null;
//        }
//        return order.status.eq(statusCond);
//    }
//
//    private BooleanExpression nameLike(String nameCond) {
//        if(!StringUtils.hasText(nameCond)){
//            return null;
//        }
//        return member.name.like(nameCond);
//    }
}
