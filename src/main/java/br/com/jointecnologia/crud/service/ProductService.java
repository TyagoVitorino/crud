package br.com.jointecnologia.crud.service;

import br.com.jointecnologia.crud.exceptions.ResourceNotFoundException;
import br.com.jointecnologia.crud.model.ProductModel;
import br.com.jointecnologia.crud.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing Product entities.
 * Provides business logic and interacts with {@link ProductRepository}.
 * <p>
 * The ProductService class contains methods for performing CRUD operations on Product entities,
 * such as creating, fetching, updating, and deleting products. It uses the {@link ProductRepository} for data persistence.
 * </p>
 * <p>
 * The primary purpose of this service is to abstract the interaction with the repository and provide a clear API
 * for controllers to interact with.
 * </p>
 *
 * @see ProductRepository
 * @since 2024-11-09
 */
@Service
@Slf4j
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Retrieves all products from the database.
     * <p>
     * This method queries the repository to fetch all product records stored in the database.
     * </p>
     *
     * @return a list of {@link ProductModel} representing all products.
     */
    public List<ProductModel> getAllProducts() {
        log.info("Fetching all products from the database");
        List<ProductModel> products = productRepository.findAll();
        log.debug("Fetched {} products", products.size());
        return products;
    }

    /**
     * Retrieves a product by its ID.
     * <p>
     * This method queries the repository to fetch a product based on the given ID.
     * If the product is not found, a {@link ResourceNotFoundException} is thrown.
     * </p>
     *
     * @param id the ID of the product to be fetched.
     * @return the {@link ProductModel} representing the product if found.
     * @throws ResourceNotFoundException if no product with the specified ID exists.
     */
    public ProductModel getProductById(Long id) {
        log.info("Fetching product with ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    /**
     * Creates a new product and saves it to the database.
     * <p>
     * This method accepts a {@link ProductModel} object, saves it to the database, and returns the saved entity.
     * </p>
     *
     * @param product the product to be created.
     * @return the {@link ProductModel} that was saved to the database.
     */
    public ProductModel createProduct(ProductModel product) {
        log.info("Creating new product: {}", product.getName());
        ProductModel savedProduct = productRepository.save(product);
        log.debug("Product created with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    /**
     * Updates an existing product in the database.
     * <p>
     * This method checks if the product with the specified ID exists in the repository.
     * If the product is not found, a {@link ResourceNotFoundException} is thrown.
     * Otherwise, it updates the product with the new data and saves it back to the repository.
     * </p>
     *
     * @param product the {@link ProductModel} object containing the updated product information.
     * @return the updated {@link ProductModel} entity.
     * @throws ResourceNotFoundException if no product with the specified ID exists.
     */
    public ProductModel updateProduct(ProductModel product) {
        log.info("Updating product with ID: {}", product.getId());
        if (!productRepository.existsById(product.getId())) {
            log.warn("Product with ID: {} not found", product.getId());
            throw new ResourceNotFoundException("Product", product.getId());
        }
        ProductModel updatedProduct = productRepository.save(product);
        log.debug("Product with ID: {} updated", updatedProduct.getId());
        return updatedProduct;
    }

    /**
     * Deletes a product by its ID.
     * <p>
     * This method deletes the product with the specified ID from the database.
     * </p>
     *
     * @param id the ID of the product to be deleted.
     */
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        log.debug("Product with ID: {} deleted successfully", id);
    }
}
