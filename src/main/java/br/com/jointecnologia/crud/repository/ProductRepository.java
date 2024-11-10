package br.com.jointecnologia.crud.repository;

import br.com.jointecnologia.crud.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing Product entities in the database.
 * Extends JpaRepository to provide CRUD operations.
 *
 * @see ProductModel
 * @since 2024-11-09
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findByCategoryId(Long categoryId);
}
