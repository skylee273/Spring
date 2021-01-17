package study.studyspring.Service;

import study.studyspring.domain.Member;
import study.studyspring.repository.MemberRepository;
import study.studyspring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// 서비스에 맞는 네이밍 잡는다.
// Test 만들기 단추기 ctrl + shift + T
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원가입
     * 비즈니스 로직 : 같은 중복 회원 안된다.
     * @param member
     * @return
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원X
        vailDateDuplicateMember(member); // 중복회원 (Extractor Method : ctal + alt + m)
        memberRepository.save(member);
        return member.getId();
    }

    private void vailDateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
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
