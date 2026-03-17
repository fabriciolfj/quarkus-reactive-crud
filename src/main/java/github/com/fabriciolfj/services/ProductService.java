package github.com.fabriciolfj.services;

import github.com.fabriciolfj.entities.Product;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
public class ProductService {

    public Uni<Product> findByIdProduct(final Long id) {
        return Product.<Product>findById(id)
                .ifNoItem()
                .after(Duration.ofSeconds(2))
                .fail();
    }

    @WithTransaction
    public Uni<Void> save(final Product product) {
        return Uni.createFrom().item(product)
                .onItem()
                .transformToUni(v -> v.persist())
                .invoke(prod -> log.info("product save successfully {}", prod))
                .replaceWithVoid()
                .onFailure()
                .invoke(c ->  log.error("fail save product {}, cause {}", product.getName(), c.getMessage()));
    }
}