package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 통합 테스트 보다 단위 테스트가 더 좋을 확률 높음!

@SpringBootTest
@Transactional // 테스트 후 rollback 기능
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    // 테스트케이스는 간단히 필드 주입 !

    @Test
    void 회원가입() {
        // given 주어졌을 때
        Member member = new Member();
        member.setName("spring100");

        // when 실행했을 때
        Long saveId = memberService.join(member);

        // then 결과
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // 예외 처리 (화살표 기준 오른쪽 메서드를 실행하는데 왼쪽 예외가 발생해야함)
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 리턴값 받아서 메세지 검증
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


    }

}