package study.studyspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.studyspring.Service.MemberService;
import study.studyspring.repository.JdbcMemberRepository;
import study.studyspring.repository.JdbcTemplateMemberRepository;
import study.studyspring.repository.MemberRepository;
import study.studyspring.repository.MemoryMemberRepository;

import javax.sql.DataSource;

// 자바코드로 직접 스프링 빈에 등록하는 방법

/**
 * 예전에는 XML로 등록했는데 현재 실무에서는 대부분 자바로 설정함
 *
 */
@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);

        return new JdbcTemplateMemberRepository(dataSource);
    }

}
