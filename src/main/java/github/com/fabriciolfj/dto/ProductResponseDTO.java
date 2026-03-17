package github.com.fabriciolfj.dto;

import lombok.Builder;

@Builder
public class ProductResponseDTO {

    private String name;

    public ProductResponseDTO() { }

    public ProductResponseDTO(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
