import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    public void firstMono(){
        Mono.just("A");
    }
    @Test
    void firstMonoWithConsumer(){
        Mono.just("A").log().subscribe(System.out::println);
    }
    @Test
    void firstMonoWithDoOn(){
        Mono.just("A")
                .log()
                .doOnSubscribe(System.out::println)
                .doOnError(System.out::println)
                .doOnRequest((c) -> System.out.println("Request " + c))
                .doOnSuccess(System.out::println)
                .subscribe();
    }
    @Test
    void firstEmptyMono(){
        Mono.empty()
                .log()
                .subscribe(System.out::println, null, () -> System.out.println("Done"));
    }
    @Test
    void monoWithError(){
        Mono.error(RuntimeException::new)
                .log()
                .subscribe(System.out::println, System.out::println);
    }
    @Test
    void monoWithErrorResume(){
        Mono.error(RuntimeException::new)
                .onErrorResume(error -> {
                    System.out.println(error);
                    return Mono.just("B");
                })
                .log()
                .subscribe(System.out::println);
    }
    @Test
    void monoWithErrorReturn(){
        Mono.error(RuntimeException::new)
                .onErrorReturn("B")
                .log()
                .subscribe(System.out::println);
    }
}
