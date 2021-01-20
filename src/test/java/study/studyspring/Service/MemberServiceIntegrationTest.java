package study.studyspring.Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import study.studyspring.domain.Member;
import study.studyspring.repository.MemberRepository;
import study.studyspring.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Spring Container 테스트 보다는 단위 테스트 ( 단순 자바 객체만으로 ) 테스트 하는 것이 더 좋은 테스트 이다.
@SpringBootTest
@Transactional // 데이터 트랜잭션 해주고 그다음 롤백 해줌
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("TEST");

        //when
        Long saveId = memberService.join(member);

        //then
        memberService.findOne(saveId).get();
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("TEST");

        Member member2 = new Member();
        member2.setName("TEST");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then
    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}