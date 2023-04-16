package hello.advanced.trace.strategy.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 필드에 전략을 보관하는 방식
 * ContextV1 는 변하지 않는 로직을 가지고 있는 템플릿 역할으 하는 코드이다.
 * 이것을 컨텍스트(문맥) 이라고 한다.
 * 문맥은 변하지 않으나 strategy 를 통해 일부 전략이 변경된다고 생각하면 된다.
 *
 * Context 는 내부에 Strategy 필드르 가지고 있다.
 * 이 필드에 변하는 부분인 Strategy 의 구현체를 주입하면 된다.
 * Context 는 Strategy 인프테이스에만 의존하고있어
 * Strategy 의 구현체를 변경하더라고 해도 Context 에 영향을 주지 않는다.
 * -> 스프링에서 의존관계 주입에서 사용하는 패턴이 전략패턴이다.
 *
 * => 상속 (템플릿 메서드 패턴) 보다는 위임(전략 패턴)을 사용한다.
 */

/**
 * 필드에 전략을 보관하는 방식
 */
@Slf4j
public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        strategy.call(); // 위임
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
