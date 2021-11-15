import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class OperatorsTest {

    @Test
    void firstOperator(){
        Flux.range(1, 10)
                .log()
                .map(value -> value * 10)
                .subscribe(System.out::println);
    }
    @Test
    void flatMap(){
        Flux.range(1, 10)
                .log()
                .flatMap(value -> Flux.range(value * 2, 4))
                .subscribe(System.out::println);
    }
    @Test
    void flatMapMany(){
        Flux.range(1, 10)
                .log()
                .flatMapSequential( value -> Flux.range(1, value))
                .subscribe(System.out::println);
    }
    @Test
    void concatFlux() throws InterruptedException {
        Flux<Integer> integerFlux = Flux.range(6, 9)
                .log()
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> integerFlux1 = Flux.range(10, 15)
                .log()
                .delayElements(Duration.ofMillis(400));
        Flux.concat(integerFlux, integerFlux1)
                .subscribe(System.out::println);
        Thread.sleep(10000);
    }
    @Test
    void mergeFlux() throws InterruptedException {
        Flux<Integer> integerFlux = Flux.range(6, 9)
                .log()
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> integerFlux1 = Flux.range(10, 15)
                .log()
                .delayElements(Duration.ofMillis(400));
        Flux.merge(integerFlux, integerFlux1)
                .subscribe(System.out::println);
        Thread.sleep(10000);
    }
    @Test
    void zipFlux(){
        Flux<Integer> integerFlux = Flux.range(6, 9)
                .log();
        Flux<Integer> integerFlux1 = Flux.range(10, 15)
                .log();
        Flux.zip(integerFlux, integerFlux1, (item1, item2) -> item1 + ", " + item2)
                .subscribe(System.out::println);
    }
}
