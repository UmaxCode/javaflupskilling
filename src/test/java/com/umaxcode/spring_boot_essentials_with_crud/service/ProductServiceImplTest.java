package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.exception.ServiceException;
import com.umaxcode.spring_boot_essentials_with_crud.repository.ProductRepository;
import com.umaxcode.spring_boot_essentials_with_crud.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProductServiceImpl productService;

    private Authentication authentication;


    @BeforeEach
    void setUp() {

        // Mock SecurityContext
        authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void addProduct_ShouldSaveProduct() {

        ProductDTO request = ProductDTO.builder()
                .name("testProductName")
                .description("testProductDescription")
                .build();

        User user = User.builder()
                .username("testUserName")
                .build();

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        when(authentication.getName()).thenReturn("testUserName");
        when(userService.findUserByUsername("testUserName")).thenReturn(user);

        productService.addProduct(request);

        verify(productRepository, times(1)).save(captor.capture());

        Product productResponse = captor.getValue();

        assertNotNull(productResponse);
        assertEquals("testProductName", productResponse.getName());
        assertEquals("testProductDescription", productResponse.getDescription());
        assertEquals(user, productResponse.getOwner());
    }

    @Test
    void findProductById_ShouldReturnProduct_WhenProductExists() {

        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .build();

        when(authentication.getName()).thenReturn("testUserName");
        when(productRepository.findByIdAndOwner_Username(productId, "testUserName")).thenReturn(Optional.of(product));

        Product result = productService.findProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
    }

    @Test
    void findProductById_ShouldThrowException_WhenProductDoesNotExist() {

        Long productId = 1L;

        when(authentication.getName()).thenReturn("testUserName");
        when(productRepository.findByIdAndOwner_Username(productId, "testUserName")).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> productService.findProductById(productId));
        assertEquals("Product with id = 1 not found", exception.getMessage());
    }

    @Test
    void findAllProducts_ShouldReturnListOfProducts() {

        Product product1 = new Product();
        Product product2 = new Product();

        List<Product> products = List.of(product1, product2);

        when(authentication.getName()).thenReturn("testUserName");
        when(productRepository.findAllByOwner_Username("testUserName")).thenReturn(products);

        List<Product> result = productService.findAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAllByOwner_Username("testUserName");
    }

    @Test
    void updateProduct_ShouldUpdateProductFieldsAndSave() {

        Long productId = 1L;

        Product existingProduct = Product.builder()
                .id(productId)
                .name("Old Product Name")
                .description("Old Product Description")
                .build();

        ProductDTO updateRequest = ProductDTO.builder()
                .name("New Product Name")
                .description("New Product Description")
                .build();

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        when(authentication.getName()).thenReturn("testUserName");
        when(productRepository.findByIdAndOwner_Username(productId, "testUserName"))
                .thenReturn(Optional.of(existingProduct));

        productService.updateProduct(productId, updateRequest);

        verify(productRepository, times(1)).save(captor.capture());

        Product productResponse = captor.getValue();

        assertNotNull(productResponse);
        assertEquals("New Product Name", productResponse.getName());
        assertEquals("New Product Description", productResponse.getDescription());
    }

    @Test
    void updateProduct_ShouldThrowException_WhenProductNotFound() {

        Long productId = 1L;

        ProductDTO updateRequest = ProductDTO.builder()
                .name("New Product Name")
                .description("New Product Description")
                .build();

        when(authentication.getName()).thenReturn("testUserName");

        ServiceException exception = assertThrows(ServiceException.class, () ->
                productService.updateProduct(productId, updateRequest));

        assertEquals("Product with id = 1 not found", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    @WithMockUser(username = "testUserName")
    void deleteProduct_ShouldDeleteProduct_WhenProductExists() {

        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .build();

        when(authentication.getName()).thenReturn("testUserName");
        when(productRepository.findByIdAndOwner_Username(productId, "testUserName")).thenReturn(Optional.of(product));

        String result = productService.deleteProduct(productId);

        assertEquals("Product with id = 1 is deleted successfully", result);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenProductDoesNotExist() {

        Long productId = 1L;

        when(authentication.getName()).thenReturn("testUser");
        when(productRepository.findByIdAndOwner_Username(productId, "testUser")).thenReturn(Optional.empty());


        ServiceException exception = assertThrows(ServiceException.class, () -> productService.deleteProduct(productId));
        assertEquals("Product with id = 1 not found", exception.getMessage());
        verify(productRepository, never()).delete(any(Product.class));
    }
}