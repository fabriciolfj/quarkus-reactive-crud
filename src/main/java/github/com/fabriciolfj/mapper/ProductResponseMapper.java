package github.com.fabriciolfj.mapper;

import github.com.fabriciolfj.dto.ProductResponseDTO;
import github.com.fabriciolfj.entities.Product;

import java.util.List;

public class ProductResponseMapper {

    private ProductResponseMapper() { }

    public static List<ProductResponseDTO> toDTO(final List<Product> products) {
        return products.stream().map(v -> ProductResponseDTO.builder().name(v.getName()).build())
                .toList();
    }
}
