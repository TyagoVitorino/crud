package br.com.jointecnologia.crud.repository;

import br.com.jointecnologia.crud.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing and managing Category entities in the database.
 * Extends JpaRepository to provide CRUD operations.
 *
 * @see CategoryModel
 * @since 2024-11-09
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    Optional<CategoryModel> findByName(String name);
}
