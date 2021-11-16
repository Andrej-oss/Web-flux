package com.sollers.springreactiveweblesson.handler;

import com.sollers.springreactiveweblesson.entity.Product;
import com.sollers.springreactiveweblesson.entity.ProductEvent;
import com.sollers.springreactiveweblesson.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ProductHandler {

    private ProductRepository repository;

    public ProductHandler() {
    }

    @Autowired
    public ProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        Flux<Product> products = repository.findAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products, Product.class);
    }

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Product> product = repository.findById(id);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product, Product.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        Mono<Product> product = request.bodyToMono(Product.class);

        return product.flatMap(value -> {
            return ServerResponse.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(repository.save(value), Product.class);
        });
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        Mono<Product> product = request.bodyToMono(Product.class);
        String id = request.pathVariable("id");
        Mono<Product> existProduct = repository.findById(id);

        return product
                .zipWith(existProduct,
                        (productRequest, existedProduct) ->
                                new Product(existedProduct.getId(), productRequest.getName(), productRequest.getPrice()))
                .flatMap(newProduct -> {
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(repository.save(newProduct), Product.class)
                            .switchIfEmpty(ServerResponse.notFound().build());
                });
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String id = request.pathVariable("id");

        return repository.findById(id)
                .flatMap(existProduct -> {
                    return ServerResponse
                            .ok().contentType(MediaType.APPLICATION_JSON)
                            .build(repository.delete(existProduct));
                }).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getProductEvent(ServerRequest request) {
        Flux<ProductEvent> productEvent = Flux.interval(Duration.ofSeconds(1))
                .map(a -> new ProductEvent(a, "Product Event"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productEvent, ProductEvent.class);
    }
}
