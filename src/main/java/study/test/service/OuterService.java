package study.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OuterService {
    private final InnerService inner;
    public OuterService(InnerService inner) { this.inner = inner; }

    @Transactional
    public void outerHandlesInner() {
        try {
            inner.innerSaveAndFail("foo");
        } catch (RuntimeException ex) {
            // 문제: 예외를 잡아도 이미 rollback‑only 상태라 commit 시 예외 발생
            System.out.println("Caught in outer: " + ex.getMessage());
        }
        // 여기서 반환되면, 트랜잭션 인터셉터가 commit 시도 → UnexpectedRollbackException
    }
}
