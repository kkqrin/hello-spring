package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // 저장 공간
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // id를 시퀀스로 설정
        store.put(member.getId(), member); // 해쉬맵에 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null일때 아닐때 구분
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 루프 돌림
                .filter(member -> member.getName().equals(name)) // name과 같은 지 비교하여 같은 것만 필터 (람다)
                .findAny(); // 하나라도 찾으면 반환
        // 없으면 Optional에 null이 포함되어 반환
    }

    @Override
    public List<Member> findAll() {
        // 리스트 반환
        return new ArrayList<>(store.values()); // Member 객체
    }

    public void clearStore() {
        // 초기화
        store.clear();
    }
}
