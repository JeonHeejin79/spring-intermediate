package hello.advanced.app.v2;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** v2 문제점
 * - traceId 의 동기화를 위해 모든 소스 수정이 필요
 * - 로그처음은 begin() / 처음이아닐때는 beginSync() 를 따로 호출해야한다.
 * - 서비스 코드가 테스트나 다른 코드에서 호출되는경우 넘길 TraceId 가 없다.
 */
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV2.request();");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";

        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 꼭 다시 던져줘야 한다.
        }
    }
}
