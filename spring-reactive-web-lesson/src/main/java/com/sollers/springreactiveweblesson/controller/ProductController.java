/*package com.sollers.springreactiveweblesson.controller;

import com.sollers.springreactiveweblesson.entity.Product;
import com.sollers.springreactiveweblesson.entity.ProductEvent;
import com.sollers.springreactiveweblesson.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductRepository repository;

    @Autowired
    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Product> getAllProducts(){
        return repository.findAll();
    }
    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id){
        return repository.findById(id).map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping(value = "/event", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductEvent> getProductEvent(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(value -> new ProductEvent(value, "Product Event"));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product){
        return repository.save(product);
    }
    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable String id, @RequestBody Product product){
        return repository.findById(id)
                .flatMap(existProduct -> {
                    existProduct.setName(product.getName());
                    existProduct.setPrice(product.getPrice());
                    return repository.save(existProduct);
                })
                .map(product1 -> ResponseEntity.ok(product1))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id){
        return repository.findById(id)
                .flatMap(existProduct -> repository.delete(existProduct))
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}*/
