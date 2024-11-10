package br.com.jointecnologia.crud.service;

import br.com.jointecnologia.crud.exceptions.ResourceNotFoundException;
import br.com.jointecnologia.crud.model.CategoryModel;
import br.com.jointecnologia.crud.repository.CategoryRepository;
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
class CategoryServiceTest {

    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "Test Category";

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Test getAllCategories returns list of categories")
    void testGetAllCategories() {
        // Arrange
        CategoryModel category1 = new CategoryModel();
        category1.setId(1L);
        category1.setName(CATEGORY_NAME);

        CategoryModel category2 = new CategoryModel();
        category2.setId(2L);
        category2.setName("Another Category");

        List<CategoryModel> categories = List.of(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryModel> result = categoryService.getAllCategories();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(category1));
        assertTrue(result.contains(category2));
        verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("Test getCategoryById returns category when found")
    void testGetCategoryById() {
        // Arrange
        CategoryModel category = new CategoryModel();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));

        // Act
        CategoryModel result = categoryService.getCategoryById(CATEGORY_ID);

        // Assert
        assertEquals(category, result);
        verify(categoryRepository).findById(CATEGORY_ID);
    }

    @Test
    @DisplayName("Test getCategoryById throws ResourceNotFoundException when category not found")
    void testGetCategoryByIdThrowsResourceNotFoundException() {
        // Arrange
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(CATEGORY_ID));
        verify(categoryRepository).findById(CATEGORY_ID);
    }

    @Test
    @DisplayName("Test createCategory saves new category")
    void testCreateCategory() {
        // Arrange
        CategoryModel category = new CategoryModel();
        category.setName(CATEGORY_NAME);

        CategoryModel savedCategory = new CategoryModel();
        savedCategory.setId(CATEGORY_ID);
        savedCategory.setName(CATEGORY_NAME);

        when(categoryRepository.save(category)).thenReturn(savedCategory);

        // Act
        CategoryModel result = categoryService.createCategory(category);

        // Assert
        assertEquals(savedCategory, result);
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Test updateCategory updates existing category")
    void testUpdateCategory() {
        // Arrange
        CategoryModel category = new CategoryModel();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);

        CategoryModel updatedCategory = new CategoryModel();
        updatedCategory.setId(CATEGORY_ID);
        updatedCategory.setName("Updated Category");

        when(categoryRepository.existsById(CATEGORY_ID)).thenReturn(true);
        when(categoryRepository.save(category)).thenReturn(updatedCategory);

        // Act
        CategoryModel result = categoryService.updateCategory(CATEGORY_ID, category);

        // Assert
        assertEquals(updatedCategory, result);
        verify(categoryRepository).existsById(CATEGORY_ID);
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Test updateCategory throws ResourceNotFoundException when category does not exist")
    void testUpdateCategoryThrowsResourceNotFoundException() {
        // Arrange
        CategoryModel category = new CategoryModel();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);

        when(categoryRepository.existsById(CATEGORY_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory(CATEGORY_ID, category));
        verify(categoryRepository).existsById(CATEGORY_ID);
    }

    @Test
    @DisplayName("Test deleteCategory deletes category")
    void testDeleteCategory() {
        // Act
        categoryService.deleteCategory(CATEGORY_ID);

        // Assert
        verify(categoryRepository).deleteById(CATEGORY_ID);
    }
}
