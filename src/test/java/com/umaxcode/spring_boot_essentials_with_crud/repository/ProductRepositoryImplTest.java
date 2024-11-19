package com.umaxcode.spring_boot_essentials_with_crud.repository;

import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryImplTest {

    private ProductRepositoryImpl productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl();
    }

    @Test
    void save_ShouldAddProduct_WhenProductIsNotAlreadySaved() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .build();

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
        assertEquals("Test Description", savedProduct.getDescription());
        assertTrue(productRepository.findAll().contains(savedProduct));
    }

    @Test
    void save_ShouldNotAddProduct_WhenProductIsAlreadySaved() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .build();

        productRepository.save(product);
        int initialSize = productRepository.findAll().size();
        productRepository.save(product);

        assertEquals(initialSize, productRepository.findAll().size());
    }

    @Test
    void findById_ShouldReturnProduct_WhenProductWithGivenIdExists() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .build();

        Product savedProduct = productRepository.save(product);
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals(savedProduct, foundProduct.get());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenProductWithGivenIdDoesNotExist() {
        Optional<Product> foundProduct = productRepository.findById("non_existent_id");

        assertFalse(foundProduct.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllSavedProducts() {
        Product product1 = Product.builder()
                .name("Product 1")
                .description("Description 1")
                .build();

        Product product2 = Product.builder()
                .name("Product 2")
                .description("Description 2")
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productRepository.findAll();

        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    void delete_ShouldRemoveProduct_WhenProductExists() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .build();

        Product savedProduct = productRepository.save(product);
        productRepository.delete(savedProduct);

        assertFalse(productRepository.findAll().contains(savedProduct));
    }

    @Test
    void delete_ShouldDoNothing_WhenProductDoesNotExist() {
        Product product = Product.builder()
                .name("Non-existent Product")
                .description("Non-existent Description")
                .build();

        int initialSize = productRepository.findAll().size();
        productRepository.delete(product);

        assertEquals(initialSize, productRepository.findAll().size());
    }
}
