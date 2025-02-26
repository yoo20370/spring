package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public class MemberRepository {

    // 스프링부트가 해당 어노테이션이 있으면 엔티티 매니저를 주입해준다.
    @PersistenceContext
    private EntityManager em;

    // Id만 반환하는 이유 커멘드와 쿼리를 분리
    // 저장 후, 사이드 이펙트를 일으키는 커멘트성이므로 리턴값을 만들지 않는다.
    // 아이디 정도는 반환가능
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }




}
