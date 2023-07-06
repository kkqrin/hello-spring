package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        // 외부에서 객체 생성 (DI) 의존성 주입????
        this.memberRepository = memberRepository;
    }


    /**
     * 회원 가입
     */
    public Long join(Member member){
        
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 리팩토링
        // Optional<Member> result = memberRepository.findByName(member.getName())
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { // 값이 있으면(null 아닐때) 로직 실행
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        // Optional 안에 Member 객체, 여러 메소드 사용 가능
        // 그냥 꺼내고 싶으면 get() 사용 (권장x), orElseGet() 값이 있으면 꺼내고 없으면 로직(디폴트값 등) 실행
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
