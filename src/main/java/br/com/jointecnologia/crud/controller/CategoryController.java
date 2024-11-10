package br.com.jointecnologia.crud.controller;

import br.com.jointecnologia.crud.controller.dto.CategoryDTO;
import br.com.jointecnologia.crud.controller.mapper.ControllerMapper;
import br.com.jointecnologia.crud.model.CategoryModel;
import br.com.jointecnologia.crud.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/categories")
@Tag(name = "Categories", description = "Operations related to categories")
@Validated
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ControllerMapper controllerMapper;

    @GetMapping
    @Operation(tags = "category", description = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        log.info("Request to get all categories");
        List<CategoryModel> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(controllerMapper.toCategoryDTOList(categories), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(tags = "category", description = "Get a category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the category"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable @NotNull Long id) {
        log.info("Request to get category by ID: {}", id);
        CategoryModel category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(controllerMapper.toCategoryDTO(category), HttpStatus.OK);
    }

    @PostMapping
    @Operation(tags = "category", description = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the category"),
            @ApiResponse(responseCode = "400", description = "Invalid category data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        log.info("Request to create a new category: {}", categoryDTO.getName());
        CategoryModel createdCategory = categoryService.createCategory(controllerMapper.toCategoryModel(categoryDTO));
        return new ResponseEntity<>(controllerMapper.toCategoryDTO(createdCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(tags = "category", description = "Update an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the category"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid category data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable @NotNull Long id,
                                                      @RequestBody @Valid CategoryDTO categoryDTO) {
        log.info("Request to update category with ID: {}", id);
        categoryDTO.setId(id);
        CategoryModel updatedCategory = categoryService.updateCategory(id, controllerMapper.toCategoryModel(categoryDTO));
        return new ResponseEntity<>(controllerMapper.toCategoryDTO(updatedCategory), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = "category", description = "Delete a category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the category"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteCategory(@PathVariable @NotNull Long id) {
        log.info("Request to delete category with ID: {}", id);
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
