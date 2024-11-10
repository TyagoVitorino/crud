package br.com.jointecnologia.crud.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the payload for a product in the system.
 * <p>
 * This request includes the product's unique identifier, name, price, and the category to which the product belongs.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload for a product")
public class ProductDTO {

    @Schema(description = "Unique identifier for the product", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "Laptop")
    private String name;

    @Schema(description = "Price of the product", example = "799.99")
    private Double price;

    @Schema(description = "Category to which the product belongs",
            implementation = CategoryDTO.class,
            example = "{\"id\": 123, \"name\": \"Electronics\"}")
    private CategoryDTO category;
}