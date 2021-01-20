package study.studyspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.studyspring.Service.MemberService;
import study.studyspring.domain.Member;

import java.util.List;

// MVC 템플릿 엔진 이미지 확인
// 컨트롤러 어노테이션 있으면 스프링안에 생겨서 관리를 한다.
@Controller
public class MemberController {
    // 스프링이 관리를 하면 스프링 에서 받아서 써야한다.
    // 객체를 new 해서 여러개를 쓸필요가 없다 하나만 만드는게 좋다.
    // private final MemberService memberService = new MemberService();

    private final MemberService memberService;

    /**
     * @param memberService -> 스프링이 memberService를 연결해줌.
     * Spring 빈을 등록하는 2가지 방법
     * - 컴포넘트 스캔 방식
     * - 자바 코드로 직접 스프링 빈 등록하기
     */

    @Autowired // 생성자 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
    }


    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
