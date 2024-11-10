package br.com.jointecnologia.crud.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the payload for updating a category.
 * <p>
 * This request includes the unique identifier of the category, the name of the category,
 * and a list of products associated with this category.
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payload for updating a category")
public class CategoryDTO {

    @Schema(description = "Unique identifier for the category", example = "123")
    private Long id;

    @Schema(description = "Name of the category", example = "Electronics")
    private String name;
}
