package br.com.jointecnologia.crud.controller;

import br.com.jointecnologia.crud.controller.dto.ProductDTO;
import br.com.jointecnologia.crud.controller.mapper.ControllerMapper;
import br.com.jointecnologia.crud.model.ProductModel;
import br.com.jointecnologia.crud.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private static final String PRODUCT_ID = "1";
    private static final String PRODUCT_NAME = "Product 1";
    private static final String PRODUCT_ENDPOINT = "/products";
    private static final String PRODUCT_BY_ID_ENDPOINT = "/products/{id}";

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private ControllerMapper controllerMapper;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.productController).build();
    }

    @Test
    @DisplayName("Test createProduct returns status 201 when product is created successfully")
    void shouldCreateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(PRODUCT_NAME);

        ProductModel createdProduct = new ProductModel();
        createdProduct.setId(1L);
        createdProduct.setName(PRODUCT_NAME);

        when(productService.createProduct(any())).thenReturn(createdProduct);
        when(controllerMapper.toProductDTO(createdProduct)).thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PRODUCT_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(PRODUCT_NAME));
    }

    @Test
    @DisplayName("Test getProductById returns product when found")
    void shouldGetProductById() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName(PRODUCT_NAME);

        ProductModel product = new ProductModel();
        product.setId(1L);
        product.setName(PRODUCT_NAME);

        when(productService.getProductById(1L)).thenReturn(product);
        when(controllerMapper.toProductDTO(product)).thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_BY_ID_ENDPOINT, PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(PRODUCT_NAME));
    }

    @Test
    @DisplayName("Test updateProduct returns updated product when update is successful")
    void shouldUpdateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName(PRODUCT_NAME);

        ProductModel updatedProduct = new ProductModel();
        updatedProduct.setId(1L);
        updatedProduct.setName(PRODUCT_NAME);

        when(productService.updateProduct(any())).thenReturn(updatedProduct);
        when(controllerMapper.toProductDTO(updatedProduct)).thenReturn(productDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(PRODUCT_BY_ID_ENDPOINT, PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(PRODUCT_NAME));
    }

    @Test
    @DisplayName("Test getAllProducts returns list of products")
    void shouldGetAllProducts() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(PRODUCT_NAME);

        ProductModel product = new ProductModel();
        product.setId(1L);
        product.setName(PRODUCT_NAME);

        when(productService.getAllProducts()).thenReturn(List.of(product));
        when(controllerMapper.toProductDTOList(any())).thenReturn(List.of(productDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(PRODUCT_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(PRODUCT_NAME));
    }

    @Test
    @DisplayName("Test deleteProduct returns status 204 when product is deleted successfully")
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(PRODUCT_BY_ID_ENDPOINT, PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
