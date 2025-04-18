package study.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import study.test.repository.MemberRepository;
import study.test.service.MemberService;
import study.test.service.OuterService;
import study.test.service.ProxyCorrectService;
import study.test.service.SelfInvocationService;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(
//			MemberService memberService,
//			SelfInvocationService selfService,
//			ProxyCorrectService proxyService,
//			MemberRepository repo
//	) {
//		return args -> {
//			System.out.println("=== 1. 정상 롤백 테스트 ===");
//			try {
//				memberService.addAndFail("T1");
//			} catch (Exception ignore) {}
//			System.out.println("DB count after rollback: " + repo.count());  // 0
//
//			System.out.println("\n=== 2. 정상 커밋 테스트 ===");
//			memberService.addCommit("T2");
//			System.out.println("DB count after commit: " + repo.count());    // 1
//
//			System.out.println("\n=== 3. Self‑Invocation 문제 ===");
//			try {
//				selfService.outerMethod();
//			} catch (Exception ignore) {}
//			System.out.println("DB count after self-invocation: " + repo.count());
//			// addAndFail로 트랜잭션 없었으므로 롤백 안 돼, count=2
//
//			System.out.println("\n=== 4. 별도 빈 호출로 해결 ===");
//			try {
//				proxyService.callProperly();
//			} catch (Exception ignore) {}
//			System.out.println("DB count after proxy call: " + repo.count());
//			// 트랜잭션 적용되어 addWithTx 롤백 → count remains 2
//		};
//	}

	@Bean
	CommandLineRunner runner(OuterService outer, MemberRepository repo) {
		return args -> {
			System.out.println("Before count: " + repo.count()); // 0
			try {
				outer.outerHandlesInner();
			} catch (Exception e) {
				// outer 메서드는 예외를 잡았지만,
				// 트랜잭션 커밋 실패로 UnexpectedRollbackException이 올라옴
				System.err.println(">>> Runner caught: " + e.getClass().getSimpleName());
			}
			System.out.println("After count: " + repo.count());
		};
	}

}
