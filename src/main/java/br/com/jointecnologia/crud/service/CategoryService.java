package br.com.jointecnologia.crud.service;

import br.com.jointecnologia.crud.exceptions.ResourceNotFoundException;
import br.com.jointecnologia.crud.model.CategoryModel;
import br.com.jointecnologia.crud.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Category entities.
 * Provides business logic and interacts with {@link CategoryRepository}.
 * <p>
 * The CategoryService class contains methods for performing CRUD operations on Category entities,
 * such as creating, fetching, and deleting categories. It uses the {@link CategoryRepository} for data persistence.
 * </p>
 * <p>
 * The primary purpose of this service is to abstract the interaction with the repository and provide a clear API
 * for controllers to interact with.
 * </p>
 *
 * @see CategoryRepository
 * @since 2024-11-09
 */
@Service
@Slf4j
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Retrieves all categories from the database.
     * <p>
     * This method queries the repository to fetch all category records stored in the database.
     * </p>
     *
     * @return a list of {@link CategoryModel} representing all categories.
     */
    public List<CategoryModel> getAllCategories() {
        log.info("Fetching all categories from the database");
        List<CategoryModel> categories = categoryRepository.findAll();
        log.debug("Fetched {} categories", categories.size());
        return categories;
    }

    /**
     * Retrieves a category by its ID.
     * <p>
     * This method queries the repository to fetch a category based on the given ID.
     * If the category is not found, an empty {@link Optional} is returned.
     * </p>
     *
     * @param id the ID of the category to be fetched.
     * @return an {@link Optional<CategoryModel>} containing the category if found, or an empty Optional if not found.
     */
    public CategoryModel getCategoryById(Long id) {
        log.info("Fetching category with ID: {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    /**
     * Creates a new category and saves it to the database.
     * <p>
     * This method accepts a {@link CategoryModel} object, saves it to the database, and returns the saved entity.
     * </p>
     *
     * @param category the category to be created.
     * @return the {@link CategoryModel} that was saved to the database.
     */
    public CategoryModel createCategory(CategoryModel category) {
        log.info("Creating new category: {}", category.getName());
        CategoryModel savedCategory = categoryRepository.save(category);
        log.debug("Category created with ID: {}", savedCategory.getId());
        return savedCategory;
    }

    /**
     * Updates an existing category with the given ID.
     * <p>
     * This method checks if the category with the specified ID exists in the repository.
     * If the category is not found, a {@link ResourceNotFoundException} is thrown.
     * Otherwise, it updates the category with the new data and saves it back to the repository.
     * </p>
     *
     * @param id       the ID of the category to be updated.
     * @param category the {@link CategoryModel} object containing the updated information.
     * @return the {@link CategoryModel} object representing the updated category.
     * @throws ResourceNotFoundException if no category with the specified ID exists.
     */
    public CategoryModel updateCategory(Long id, CategoryModel category) {
        log.info("Updating category with ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            log.warn("Category with ID: {} not found", id);
            throw new ResourceNotFoundException("Category", id);
        }
        category.setId(id);
        CategoryModel updatedCategory = categoryRepository.save(category);
        log.info("Category updated with ID: {}", updatedCategory.getId());
        return updatedCategory;
    }

    /**
     * Deletes a category by its ID.
     * <p>
     * This method deletes the category with the specified ID from the database.
     * </p>
     *
     * @param id the ID of the category to be deleted.
     */
    public void deleteCategory(Long id) {
        log.info("Deleting category with ID: {}", id);
        categoryRepository.deleteById(id);
        log.debug("Category with ID: {} deleted successfully", id);
    }
}
