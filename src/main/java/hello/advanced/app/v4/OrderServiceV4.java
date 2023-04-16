package hello.advanced.app.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbtractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepositoryVO;
    private final LogTrace trace;

    public void orderItem(String itemId) {

        AbtractTemplate<Void> template = new AbtractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepositoryVO.save(itemId);
                return null;
            }
        };
        template.execute("OrderServiceV4.orderItem();");
    }
}
