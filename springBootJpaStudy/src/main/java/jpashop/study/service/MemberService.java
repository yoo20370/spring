package jpashop.study.service;

import jpashop.study.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    Long join(Member member);

    List<Member> findMembers();

    Member findOne(Long id);
}
