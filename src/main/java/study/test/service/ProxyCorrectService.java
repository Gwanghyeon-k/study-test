package study.test.service;

import org.springframework.stereotype.Service;

@Service
public class ProxyCorrectService {
    private final SelfInvocationService self;
    public ProxyCorrectService(SelfInvocationService self) {
        this.self = self;
    }

    public void callProperly() {
        // Spring 컨테이너에 등록된 SelfInvocationService 프록시를 통해 호출 → 트랜잭션 적용
        self.addWithTx("withTx");
    }
}

