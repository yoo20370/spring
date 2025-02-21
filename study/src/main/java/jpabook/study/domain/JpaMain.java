package jpabook.study.domain;

import jakarta.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.jpa.internal.PersistenceUnitUtilImpl;

import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction ts = em.getTransaction();

        ts.begin();
        try {

            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressesHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressesHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===============Start================");
            Member findMember = em.find(Member.class, member.getId());

            //homeCity -> newCity,
            // 불변 객체를 유지해야하므로 이 방법은 안 됨 -> side Effect 발생
//            findMember.getHomeAddress().setCity("newCity");

            // 값 타입을 통으로 갈아끼워야 한다.
            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipcode()));

            // 치킨 -> 한식
            // String 자체가 값타입 -> 업데이트하지 말고 통째로 갈아 끼워야 한다.
            findMember.getFavoriteFoods().remove("치킨"); // 제거
            findMember.getFavoriteFoods().add("한식"); // 새로 삽입

            List<AddressEntity> addressesHistorys =  findMember.getAddressesHistory();

            // equals(), hashcode() 중요, remove시 eqauls()로 비교해서 찾기 때문
            addressesHistorys.remove (new AddressEntity("old1", "street", "10000"));
            addressesHistorys.add(new AddressEntity("newCity", "street", "10000"));

            ts.commit();
        } catch (Exception e) {
            ts.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getName();

    }
}
