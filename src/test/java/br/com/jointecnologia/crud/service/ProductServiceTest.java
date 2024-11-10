package br.com.jointecnologia.crud.service;

import br.com.jointecnologia.crud.exceptions.ResourceNotFoundException;
import br.com.jointecnologia.crud.model.ProductModel;
import br.com.jointecnologia.crud.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "Test Product";

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Test getAllProducts returns list of products")
    void testGetAllProducts() {
        // Arrange
        ProductModel product1 = new ProductModel();
        product1.setId(PRODUCT_ID);
        product1.setName(PRODUCT_NAME);

        ProductModel product2 = new ProductModel();
        product2.setId(2L);
        product2.setName("Another Product");

        List<ProductModel> products = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<ProductModel> result = productService.getAllProducts();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Test getProductById returns product when found")
    void testGetProductById() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

        // Act
        ProductModel result = productService.getProductById(PRODUCT_ID);

        // Assert
        assertEquals(product, result);
        verify(productRepository).findById(PRODUCT_ID);
    }

    @Test
    @DisplayName("Test getProductById throws ResourceNotFoundException when product not found")
    void testGetProductByIdThrowsResourceNotFoundException() {
        // Arrange
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(PRODUCT_ID));
        verify(productRepository).findById(PRODUCT_ID);
    }

    @Test
    @DisplayName("Test createProduct saves new product")
    void testCreateProduct() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setName(PRODUCT_NAME);

        ProductModel savedProduct = new ProductModel();
        savedProduct.setId(PRODUCT_ID);
        savedProduct.setName(PRODUCT_NAME);

        when(productRepository.save(product)).thenReturn(savedProduct);

        // Act
        ProductModel result = productService.createProduct(product);

        // Assert
        assertEquals(savedProduct, result);
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Test updateProduct updates existing product")
    void testUpdateProduct() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);

        ProductModel updatedProduct = new ProductModel();
        updatedProduct.setId(PRODUCT_ID);
        updatedProduct.setName("Updated Product");

        when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);
        when(productRepository.save(product)).thenReturn(updatedProduct);

        // Act
        ProductModel result = productService.updateProduct(product);

        // Assert
        assertEquals(updatedProduct, result);
        verify(productRepository).existsById(PRODUCT_ID);
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Test updateProduct throws ResourceNotFoundException when product does not exist")
    void testUpdateProductThrowsResourceNotFoundException() {
        // Arrange
        ProductModel product = new ProductModel();
        product.setId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);

        when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(product));
        verify(productRepository).existsById(PRODUCT_ID);
    }

    @Test
    @DisplayName("Test deleteProduct deletes product")
    void testDeleteProduct() {
        // Act
        productService.deleteProduct(PRODUCT_ID);

        // Assert
        verify(productRepository).deleteById(PRODUCT_ID);
    }
}
