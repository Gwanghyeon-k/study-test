package study.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.test.domain.Member;
import study.test.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository repo;

    public MemberService(MemberRepository repo) { this.repo = repo; }

    @Transactional
    public void addAndFail(String name) {
        repo.save(new Member(name));
        // 강제 예외 발생 → 트랜잭션 롤백
        throw new RuntimeException("실패");
    }

    @Transactional
    public void addCommit(String name) {
        repo.save(new Member(name));
        // 예외 없이 정상 종료 → 커밋
    }
}

