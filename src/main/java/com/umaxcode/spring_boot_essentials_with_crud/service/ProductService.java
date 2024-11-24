package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;

import java.util.List;

/**
 * Service interface for managing products.
 */
public interface ProductService {

    /**
     * Adds a new product.
     *
     * @param request the product details to be added.
     * @return the details of the added product.
     */
    Product addProduct(ProductDTO request);

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the unique identifier of the product.
     * @return the details of the found product.
     */
    Product findProductById(Long id);

    /**
     * Retrieves all products.
     *
     * @return a list of all product details.
     */
    List<Product> findAllProducts();

    /**
     * Updates an existing product.
     *
     * @param id      the unique identifier of the product to be updated.
     * @param request the updated product details.
     * @return the details of the updated product.
     */
    Product updateProduct(Long id, ProductDTO request);

    /**
     * Deletes a product by its unique identifier.
     *
     * @param id the unique identifier of the product to be deleted.
     * @return a confirmation message deleted product.
     */
    String deleteProduct(Long id);

    /**
     * Allows admin to find a user's product by its unique identifier.
     *
     * @param id the unique identifier of the product.
     * @return the details of the found product.
     */
    Product getUserProduct(Long id);

    /**
     * Allows admin to retrieve all products in the system.
     *
     * @return a list of all product details.
     */
    List<Product> findAllUserProducts();

    /**
     * Allows admin to update any product in the system.
     *
     * @param id      the unique identifier of the product to be updated.
     * @param request the updated product details.
     * @return the details of the updated product.
     */
    Product updateUserProduct(Long id, ProductDTO request);

    /**
     * Allows admin to delete any product by its unique identifier.
     *
     * @param id the unique identifier of the product to be deleted.
     * @return a confirmation message deleted product.
     */
    String deleteUserProduct(Long id);
}
