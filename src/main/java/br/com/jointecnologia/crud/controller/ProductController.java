package br.com.jointecnologia.crud.controller;

import br.com.jointecnologia.crud.controller.dto.ProductDTO;
import br.com.jointecnologia.crud.controller.mapper.ControllerMapper;
import br.com.jointecnologia.crud.model.ProductModel;
import br.com.jointecnologia.crud.service.ProductService;
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
@RequestMapping("/products")
@Tag(name = "Products", description = "Operations related to products")
@Validated
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ControllerMapper controllerMapper;

    @GetMapping
    @Operation(tags = "product", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all products"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Request to get all products");
        List<ProductModel> products = productService.getAllProducts();
        return new ResponseEntity<>(controllerMapper.toProductDTOList(products), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(tags = "product", description = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable @NotNull Long id) {
        log.info("Request to get product by ID: {}", id);
        ProductModel product = productService.getProductById(id);
        return new ResponseEntity<>(controllerMapper.toProductDTO(product), HttpStatus.OK);
    }

    @PostMapping
    @Operation(tags = "product", description = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the product"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO product) {
        log.info("Request to create a new product: {}", product.getName());
        ProductModel createdProduct = productService.createProduct(controllerMapper.toProductModel(product));
        return new ResponseEntity<>(controllerMapper.toProductDTO(createdProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(tags = "product", description = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the product"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable @NotNull Long id,
                                                    @RequestBody @Valid ProductModel product) {
        log.info("Request to update product with ID: {}", id);
        product.setId(id);
        ProductModel updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<>(controllerMapper.toProductDTO(updatedProduct), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(tags = "product", description = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the product"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull Long id) {
        log.info("Request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
