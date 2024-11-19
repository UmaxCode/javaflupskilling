package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductResponseDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import com.umaxcode.spring_boot_essentials_with_crud.exception.ServiceException;
import com.umaxcode.spring_boot_essentials_with_crud.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_ShouldReturnSavedProduct() {
        ProductRequestDTO request = new ProductRequestDTO("Product1", "Description of Product1");
        Product product = Product.builder()
                .id("1")
                .name("Product1")
                .description("Description of Product1")
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO result = productService.addProduct(request);

        assertNotNull(result);
        assertEquals("Product1", result.name());
        assertEquals("Description of Product1", result.description());
        assertEquals("1", result.id());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void findProductById_ShouldReturnProduct_WhenProductExists() {
        String id = "1";
        Product product = Product.builder()
                .id(id)
                .name("Product1")
                .description("Description of Product1")
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductResponseDTO result = productService.findProductById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Product1", result.name());
        assertEquals("Description of Product1", result.description());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void findProductById_ShouldThrowException_WhenProductNotFound() {
        String id = "1";
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ServiceException.class, () -> productService.findProductById(id));
        assertEquals("Product with id = " + id + " not found", exception.getMessage());

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void findAllProducts_ShouldReturnListOfProducts() {
        Product product1 = Product.builder()
                .id("1")
                .name("Product1")
                .description("Description of Product1")
                .build();

        Product product2 = Product.builder()
                .id("2")
                .name("Product2")
                .description("Description of Product2")
                .build();


        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponseDTO> result = productService.findAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        String id = "1";
        Product existingProduct = Product.builder()
                .id(id)
                .name("Product1")
                .description("Description of Product1")
                .build();

        ProductRequestDTO request = new ProductRequestDTO("Updated Product", "Updated description");

        Product updatedProduct = Product.builder()
                .id("1")
                .name("Updated Product")
                .description("Updated description")
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ProductResponseDTO result = productService.updateProduct(id, request);

        assertNotNull(result);
        assertEquals("Updated Product", result.name());
        assertEquals("Updated description", result.description());
        assertEquals(id, result.id());

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void deleteProduct_ShouldInvokeDelete_WhenProductExists() {
        String id = "1";
        Product product = Product.builder()
                .id(id)
                .name("Product1")
                .description("Description of Product1")
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        String result = productService.deleteProduct(id);

        assertNotNull(result);
        assertEquals("Product with id = " + id + "is deleted successfully", result);

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenProductNotFound() {
        String id = "1";
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ServiceException.class, () -> productService.deleteProduct(id));
        assertEquals("Product with id = " + id + " not found", exception.getMessage());

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(0)).delete(any(Product.class));
    }
}