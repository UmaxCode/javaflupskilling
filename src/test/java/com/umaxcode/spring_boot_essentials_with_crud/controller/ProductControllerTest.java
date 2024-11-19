package com.umaxcode.spring_boot_essentials_with_crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductResponseDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import com.umaxcode.spring_boot_essentials_with_crud.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProduct_ShouldReturnCreatedProduct_WhenRequestIsValid() throws Exception {

        ProductRequestDTO request = new ProductRequestDTO("Product1", "Description of Product1");
        Product product = Product.builder()
                .id("1")
                .name("Product1")
                .description("Description of Product1")
                .build();
        ProductResponseDTO response = new ProductResponseDTO(product.getId(), product.getName(),
                product.getDescription());
        when(productService.addProduct(any(ProductRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.description").value("Description of Product1"));
    }

    @Test
    void getProduct_ShouldReturnProduct_WhenProductExists() throws Exception {

        String id = "1";
        Product product = Product.builder()
                .name("Product1")
                .description("Description of Product1")
                .build();
        product.setId(id);

        ProductResponseDTO response = new ProductResponseDTO(product.getId(), product.getName(),
                product.getDescription());

        when(productService.findProductById(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Product1"))
                .andExpect(jsonPath("$.description").value("Description of Product1"));
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception {

        Product product1 = Product.builder()
                .id("1")
                .name("Product1")
                .description("Description of Product1")
                .build();

        ProductResponseDTO productRes1 = new ProductResponseDTO(
                product1.getId(),
                product1.getName(),
                product1.getDescription()
        );

        Product product2 = Product.builder()
                .id("2")
                .name("Product2")
                .description("Description of Product2")
                .build();

        ProductResponseDTO productRes2 = new ProductResponseDTO(
                product2.getId(),
                product2.getName(),
                product2.getDescription()
        );

        List<ProductResponseDTO> products = Arrays.asList(productRes1, productRes2);

        when(productService.findAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct_WhenRequestIsValid() throws Exception {

        String id = "1";
        ProductRequestDTO request = new ProductRequestDTO("Updated Product", "Updated Description");
        Product updatedProduct = Product.builder()
                .id("1")
                .name("Updated Product")
                .description("Updated Description")
                .build();

        ProductResponseDTO productResponse = new ProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription()
        );
        when(productService.updateProduct(eq(id), any(ProductRequestDTO.class))).thenReturn(productResponse);

        mockMvc.perform(put("/api/v1/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void deleteProduct_ShouldReturnConfirmationMessage_WhenProductIsDeleted() throws Exception {

        String id = "1";
        when(productService.deleteProduct(id)).thenReturn("Product with id = " + id + " is deleted successfully");

        mockMvc.perform(delete("/api/v1/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Product with id = " + id + " is deleted successfully"));
    }
}