package study.studyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.studyspring.domain.Member;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // select m from Member m where m.name = ?
    // 단순한 쿼리는 interface 만으로 끝난다!!!!! JPA 공부 필수 !!

    @Override
    Optional<Member> findByName(String name);


}

