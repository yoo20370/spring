package yoo.study.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{

    public static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        Long memberId = member.getId();
        store.put(memberId, member);
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }
}
