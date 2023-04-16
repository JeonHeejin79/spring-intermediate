package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** v2 문제점
 * - traceId 의 동기화를 위해 모든 소스 수정이 필요
 * - 로그처음은 begin() / 처음이아닐때는 beginSync() 를 따로 호출해야한다.
 * - 서비스 코드가 테스트나 다른 코드에서 호출되는경우 넘길 TraceId 가 없다.
 *
 * v3 문제점
 * - 동시성문제
 */
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController3.request();");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";

        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 꼭 다시 던져줘야 한다.
        }
    }
}
