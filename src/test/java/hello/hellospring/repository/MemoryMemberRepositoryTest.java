package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();
    // MemberRepository에는 없는 clearStore() 실행하기 위해 MemoryMemberRepository(구현클래스) 객체 생성..

    // 메소드 실행 끝날때마다 호출됨(콜백)
    @AfterEach
    public void afterEach(){
        repository.clearStore(); // 저장소 지우기 (초기화)
    }


    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member); // 회원 저장

        Member result = repository.findById(member.getId()).get(); // 아이디로 찾아서 꺼내기

        // 검증
        // System.out.println("result = " + (result == member));
        // Assertions.assertEquals(member, result); // (기댓값, 실제값)
        assertThat(member).isEqualTo(result); // (실제값 equal 기댓값)
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // Optional<Member> result = repository.findByName("spring1");
        Member result = repository.findByName("spring1").get(); // get() 사용시 Optional을 꺼낼 수 있음

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
