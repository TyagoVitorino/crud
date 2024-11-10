package br.com.jointecnologia.crud.controller;

import br.com.jointecnologia.crud.controller.dto.CategoryDTO;
import br.com.jointecnologia.crud.controller.mapper.ControllerMapper;
import br.com.jointecnologia.crud.model.CategoryModel;
import br.com.jointecnologia.crud.service.CategoryService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private static final String CATEGORY_ID = "1";
    private static final String CATEGORY_NAME = "Category 1";
    private static final String CATEGORY_ENDPOINT = "/categories";
    private static final String CATEGORY_BY_ID_ENDPOINT = "/categories/{id}";

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ControllerMapper controllerMapper;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.categoryController).build();
    }

    @Test
    @DisplayName("Test createCategory returns status 201 when category is created successfully")
    void shouldCreateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(CATEGORY_NAME);

        CategoryModel createdCategory = new CategoryModel();
        createdCategory.setId(1L);
        createdCategory.setName(CATEGORY_NAME);

        when(categoryService.createCategory(any())).thenReturn(createdCategory);
        when(controllerMapper.toCategoryDTO(createdCategory)).thenReturn(categoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(CATEGORY_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(CATEGORY_NAME));
    }

    @Test
    @DisplayName("Test getCategoryById returns category when found")
    void shouldGetCategoryById() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName(CATEGORY_NAME);

        CategoryModel category = new CategoryModel();
        category.setId(1L);
        category.setName(CATEGORY_NAME);

        when(categoryService.getCategoryById(1L)).thenReturn(category);
        when(controllerMapper.toCategoryDTO(category)).thenReturn(categoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORY_BY_ID_ENDPOINT, CATEGORY_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(CATEGORY_NAME));
    }

    @Test
    @DisplayName("Test updateCategory returns updated category when update is successful")
    void shouldUpdateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName(CATEGORY_NAME);

        CategoryModel updatedCategory = new CategoryModel();
        updatedCategory.setId(1L);
        updatedCategory.setName(CATEGORY_NAME);

        when(categoryService.updateCategory(anyLong(), any())).thenReturn(updatedCategory);
        when(controllerMapper.toCategoryDTO(updatedCategory)).thenReturn(categoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(CATEGORY_BY_ID_ENDPOINT, CATEGORY_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(categoryDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(CATEGORY_NAME));
    }


    @Test
    @DisplayName("Test getAllCategories returns list of categories")
    void shouldGetAllCategories() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(CATEGORY_NAME);

        CategoryModel category = new CategoryModel();
        category.setId(1L);
        category.setName(CATEGORY_NAME);

        when(categoryService.getAllCategories()).thenReturn(List.of(category));
        when(controllerMapper.toCategoryDTOList(any())).thenReturn(List.of(categoryDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(CATEGORY_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(CATEGORY_NAME));
    }

    @Test
    @DisplayName("Test deleteCategory returns status 204 when category is deleted successfully")
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(CATEGORY_BY_ID_ENDPOINT, CATEGORY_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
