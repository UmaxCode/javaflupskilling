package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO addProduct(ProductRequestDTO request);

    ProductResponseDTO findProductById(String id);

    List<ProductResponseDTO> findAllProducts();

    ProductResponseDTO updateProduct(String id, ProductRequestDTO request);

    String deleteProduct(String id);
}
