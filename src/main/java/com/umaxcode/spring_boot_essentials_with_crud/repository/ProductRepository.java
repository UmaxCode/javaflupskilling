package com.umaxcode.spring_boot_essentials_with_crud.repository;

import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing product data.
 */
public interface ProductRepository {

    /**
     * Saves a product to any data store.
     *
     * @param product the product to be saved.
     * @return the saved product.
     */
    Product save(Product product);

    /**
     * Finds a product by its unique identifier from the data store.
     *
     * @param id the unique identifier of the product.
     * @return an {@link Optional} containing the found product, or empty if no product is found.
     */
    Optional<Product> findById(String id);

    /**
     * Retrieves all products from the data store.
     *
     * @return a list of all products.
     */
    List<Product> findAll();

    /**
     * Deletes a product from the data store.
     *
     * @param product the product to be deleted.
     */
    void delete(Product product);
}
