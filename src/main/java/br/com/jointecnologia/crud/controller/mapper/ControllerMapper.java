package br.com.jointecnologia.crud.controller.mapper;

import br.com.jointecnologia.crud.controller.dto.CategoryDTO;
import br.com.jointecnologia.crud.controller.dto.ProductDTO;
import br.com.jointecnologia.crud.model.CategoryModel;
import br.com.jointecnologia.crud.model.ProductModel;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper interface responsible for converting between DTOs and model entities
 * for {@link ProductModel} and {@link CategoryModel}.
 * <p>
 * This interface defines methods for mapping:
 * - {@link ProductModel} to {@link ProductDTO} and vice versa
 * - {@link CategoryModel} to {@link CategoryDTO} and vice versa
 * </p>
 * <p>
 * The {@link Mapper} annotation ensures that MapStruct generates the implementation for these conversions at compile time.
 * The component model is set to "spring", making the generated implementation available as a Spring bean.
 *
 * @see ProductDTO
 * @see ProductModel
 * @see CategoryDTO
 * @see CategoryModel
 */
@Mapper(componentModel = "spring")
public interface ControllerMapper {

    /**
     * Converts a {@link ProductModel} to a {@link ProductDTO}.
     *
     * @param model the {@link ProductModel} to be converted
     * @return the corresponding {@link ProductDTO}
     */
    ProductDTO toProductDTO(ProductModel model);

    /**
     * Converts a {@link ProductDTO} to a {@link ProductModel}.
     *
     * @param productDTO the {@link ProductDTO} to be converted
     * @return the corresponding {@link ProductModel}
     */
    ProductModel toProductModel(ProductDTO productDTO);

    /**
     * Converts a {@link CategoryModel} to a {@link CategoryDTO}.
     *
     * @param model the {@link CategoryModel} to be converted
     * @return the corresponding {@link CategoryDTO}
     */
    CategoryDTO toCategoryDTO(CategoryModel model);

    /**
     * Converts a {@link CategoryDTO} to a {@link CategoryModel}.
     *
     * @param categoryDTO the {@link CategoryDTO} to be converted
     * @return the corresponding {@link CategoryModel}
     */
    CategoryModel toCategoryModel(CategoryDTO categoryDTO);

    /**
     * Converts a list of {@link ProductModel} to a list of {@link ProductDTO}.
     *
     * @param models the list of {@link ProductModel} to be converted
     * @return the corresponding list of {@link ProductDTO}
     */
    List<ProductDTO> toProductDTOList(List<ProductModel> models);

    /**
     * Converts a list of {@link ProductDTO} to a list of {@link ProductModel}.
     *
     * @param productDTOs the list of {@link ProductDTO} to be converted
     * @return the corresponding list of {@link ProductModel}
     */
    List<ProductModel> toProductModelList(List<ProductDTO> productDTOs);

    /**
     * Converts a list of {@link CategoryModel} to a list of {@link CategoryDTO}.
     *
     * @param models the list of {@link CategoryModel} to be converted
     * @return the corresponding list of {@link CategoryDTO}
     */
    List<CategoryDTO> toCategoryDTOList(List<CategoryModel> models);

    /**
     * Converts a list of {@link CategoryDTO} to a list of {@link CategoryModel}.
     *
     * @param categoryDTOs the list of {@link CategoryDTO} to be converted
     * @return the corresponding list of {@link CategoryModel}
     */
    List<CategoryModel> toCategoryModelList(List<CategoryDTO> categoryDTOs);
}
