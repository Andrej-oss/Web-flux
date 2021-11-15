import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class FluxTest {

    @Test
    void firstFlux(){
        Flux.just("A", "B", "C")
                .log()
                .subscribe();
    }
    @Test
    void fluxIterable(){
        Flux.fromIterable(List.of("A", "B"))
                .log()
                .subscribe();
    }
    @Test
    void flexRange(){
        Flux.range(10, 10)
                .log()
                .subscribe();
    }
    @Test
    void fluxInterval() throws InterruptedException {
        Flux
                .interval(Duration.ofSeconds(1))
                .log()
                .take(2)
                .subscribe(System.out::println);
        Thread.sleep(10000);
    }
    @Test
    void fluxRequest(){
        Flux.range(1, 5)
                .log()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        subscription.request(4);
                    }
                });
    }
    @Test
    void fluxLimitRate(){
        Flux.range(1, 10)
                .log()
                .limitRate(6)
                .subscribe();
    }
}
