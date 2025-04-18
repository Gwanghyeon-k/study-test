package study.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.test.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
