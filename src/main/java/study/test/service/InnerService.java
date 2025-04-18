package study.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.test.domain.Member;
import study.test.repository.MemberRepository;

@Service
public class InnerService {
    private final MemberRepository repo;
    public InnerService(MemberRepository repo) { this.repo = repo; }

    @Transactional
    public void innerSaveAndFail(String name) {
        repo.save(new Member(name));
        // 여긴 rollback‑only 마킹: RuntimeException 던짐
        throw new RuntimeException("inner failure");
    }
}


