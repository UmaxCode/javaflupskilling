package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductResponseDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import com.umaxcode.spring_boot_essentials_with_crud.exception.ServiceException;
import com.umaxcode.spring_boot_essentials_with_crud.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProductService}.
 */
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO request) {

        Product productInstance = Product.builder()
                .name(request.name())
                .description(request.description())
                .build();

        Product savedProduct = productRepository.save(productInstance);

        return ProductResponseDTO.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductResponseDTO findProductById(String id) {
        Product product = findById(id);

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDTO> findAllProducts() {
        return productRepository.findAll()
                .stream().map(product -> ProductResponseDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .build())
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO request) {

        Product product = findById(id);
        product.setName(request.name() != null ? request.name() : product.getName());
        product.setDescription(request.description() != null ? request.description() : product.getDescription());
        Product updatedProduct = productRepository.save(product);

        return ProductResponseDTO.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteProduct(String id) {
        Product product = findById(id);
        productRepository.delete(product);
        return "Product with id = " + id + "is deleted successfully";
    }

    /**
     * Finds a product by its unique identifier.
     *
     * @param id the unique identifier of the product to find.
     * @return the found {@link Product}.
     * @throws ServiceException if no product with the given ID is found.
     */
    private Product findById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }

        throw new ServiceException("Product with id = " + id + " not found");
    }
}
