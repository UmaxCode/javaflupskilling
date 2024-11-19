package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductResponseDTO;

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
    ProductResponseDTO addProduct(ProductRequestDTO request);

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the unique identifier of the product.
     * @return the details of the found product.
     */
    ProductResponseDTO findProductById(String id);

    /**
     * Retrieves all products.
     *
     * @return a list of all product details.
     */
    List<ProductResponseDTO> findAllProducts();

    /**
     * Updates an existing product.
     *
     * @param id the unique identifier of the product to be updated.
     * @param request the updated product details.
     * @return the details of the updated product.
     */
    ProductResponseDTO updateProduct(String id, ProductRequestDTO request);

    /**
     * Deletes a product by its unique identifier.
     *
     * @param id the unique identifier of the product to be deleted.
     * @return a confirmation message deleted product.
     */
    String deleteProduct(String id);
}

