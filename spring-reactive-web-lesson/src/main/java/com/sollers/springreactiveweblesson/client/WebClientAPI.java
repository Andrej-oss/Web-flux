package com.sollers.springreactiveweblesson.client;

import com.sollers.springreactiveweblesson.entity.Product;
import com.sollers.springreactiveweblesson.entity.ProductEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class WebClientAPI {

    private WebClient client;

    public WebClientAPI() {
        this.client = WebClient.builder()
                .baseUrl("http://localhost:8080/product")

                .build();
        //WebClient.create("http://localhost:8080/poduct");
    }

    public static void main(String[] args) throws InterruptedException {
        WebClientAPI webClientAPI = new WebClientAPI();
        webClientAPI.saveNewProduct()
                .thenMany(webClientAPI.getAllProducts())
                .take(1)
                .flatMap(product -> webClientAPI.updateProduct(product.getId(), "Jasmin Tea", 1.49))
                .flatMap(product -> webClientAPI.deleteProduct(product.getId()))
                .thenMany(webClientAPI.getAllProductEvents())
                .subscribeOn(Schedulers.newSingle("Thread1"))
                .subscribe(System.out::println);
        //Thread.sleep(10000);
    }
    Mono<ResponseEntity<Product>> saveNewProduct() {
        return client
                .post()
                .body(Mono.just(new Product(null, "Tea", 1.99)), Product.class)
                .exchangeToMono(clientResponse -> clientResponse.toEntity(Product.class))
                .doOnSuccess(message -> System.out.println("POST*******" + message));
    }
    private Flux<Product> getAllProducts(){
        return client
                .get()
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(product -> System.out.println("GET****: " + product));
    }
    private Mono<Product> updateProduct(String id, String name, Double price){
        return client
                .put()
                .uri("/{id}", id)
                .body(Mono.just(new Product(null, name, price)), Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnSuccess(product -> System.out.println("PUT ****** " + product));
    }
    private Mono<Void> deleteProduct(String id){
        return client
                .delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(product -> System.out.println("DELETE***** " + product));
    }
    private Flux<ProductEvent> getAllProductEvents(){
        return client
                .get()
                .uri("/event")
                .retrieve()
                .bodyToFlux(ProductEvent.class);
    }
}
