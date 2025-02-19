package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    // 스프링 부트는. 스프링 컨테이너 위에서 모든 것이 동작
    // 스프링 부트가 @PersistenceContext가 있으면 EntityManager를 주입을 해준다.
    // @PersistenceContext는 EntityManager의 생명 주기 컨테이너가 관리도록 설정 - EntityManger 주입, 영속 컨텍스트 관리
    @PersistenceContext
    private EntityManager em;

    // 커맨드랑 쿼리를 분리해라
    // 저장 후, side effect를 일으키는 커맨드성이기 때문에
    // 리턴값을 만들지 않는다.
    // ID 값이 있으면 다음에 다시 조회할 수 있도록 할 수 있도록 설계
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
