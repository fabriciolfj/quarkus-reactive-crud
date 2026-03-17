package github.com.fabriciolfj.resources;

import github.com.fabriciolfj.dto.ProductResponseDTO;
import github.com.fabriciolfj.entities.Product;
import github.com.fabriciolfj.mapper.ProductResponseMapper;
import github.com.fabriciolfj.services.ProductService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestMulti;

import java.util.List;


@Slf4j
@Path("/api/v1/products")
@RequiredArgsConstructor
public class ProductResources {

    private final ProductService productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<ProductResponseDTO> listProducts() {
        return productService.listProducts()
                .onItem()
                .transform(ProductResponseMapper::toDTO)
                .onItem()
                .transformToMulti(values -> Multi.createFrom().iterable(values));
    }

    @GET
    @Path("/{id}")
    public Uni<Response> queryProduct(final Long id) {
        return productService.findByIdProduct(id)
                .onItem()
                .transform(v -> Response.ok(v).build())
                .onFailure()
                .recoverWithItem(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Uni<Response> createProduct(final Product product) {
        return Uni.createFrom().item(product)
                .flatMap(productService::save)
                .onItem().transform(_ -> Response.noContent().build())
                .onFailure()
                .recoverWithItem(() -> Response.status(Response.Status.BAD_REQUEST)
                        .build());
    }
}
