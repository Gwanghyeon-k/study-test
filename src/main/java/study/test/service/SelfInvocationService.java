package study.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.test.domain.Member;
import study.test.repository.MemberRepository;

@Service
public class SelfInvocationService {
    private final MemberRepository repo;
    public SelfInvocationService(MemberRepository repo) { this.repo = repo; }

    public void outerMethod() {
        // 이 호출은 프록시를 거치지 않음 → addWithTx는 트랜잭션 없이 실행
        addWithTx("noTx");
    }

    @Transactional
    public void addWithTx(String name) {
        repo.save(new Member(name));
        throw new RuntimeException("실패 but no rollback because no Tx");
    }
}

