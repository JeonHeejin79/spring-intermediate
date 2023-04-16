package hello.advanced.app.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbtractTemplate;
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
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    @GetMapping("/v4/request")
    public String request(String itemId) {

        AbtractTemplate<String> template = new AbtractTemplate<String>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };

        return template.execute("OrderController4.request();");
    }
}
