package com.umaxcode.spring_boot_essentials_with_crud.controller;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.AdminProductDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import com.umaxcode.spring_boot_essentials_with_crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR"})  // change to @PreAuthorized() for advanced functionality
    public ProductDTO addProduct(@RequestBody ProductDTO request) {

        Product savedProduct = productService.addProduct(request);
        return ProductDTO.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR"})
    public ProductDTO getProduct(@PathVariable("id") Long id) {

        Product retrievedProduct = productService.findProductById(id);
        return ProductDTO.builder()
                .id(retrievedProduct.getId())
                .name(retrievedProduct.getName())
                .description(retrievedProduct.getDescription())
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR"})
    public List<ProductDTO> getAllProducts() {

        return productService.findAllProducts()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .build())
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR"})
    public ProductDTO updateProduct(@PathVariable("id") Long id,
                                    @RequestBody ProductDTO request) {

        Product updatedProduct = productService.updateProduct(id, request);
        return ProductDTO.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN", "ROLE_REGULAR"})
    public String deleteProduct(@PathVariable("id") Long id) {

        return productService.deleteProduct(id);
    }

    @GetMapping("/{id}/admin")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public AdminProductDTO adminGetUserProduct(@PathVariable("id") Long id) {
        Product retrievedProduct = productService.getUserProduct(id);
        return AdminProductDTO.builder()
                .id(retrievedProduct.getId())
                .name(retrievedProduct.getName())
                .description(retrievedProduct.getDescription())
                .ownerName(retrievedProduct.getOwner().getUsername())
                .build();
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public List<AdminProductDTO> adminGetAllUserProduct() {

        List<Product> retrievedProducts = productService.findAllUserProducts();

        return retrievedProducts.stream()
                .map(product -> AdminProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .ownerName(product.getOwner().getUsername())
                        .build())
                .toList();
    }

    @PutMapping("/{id}/admin")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public AdminProductDTO adminUpdateUserProduct(@PathVariable("id") Long id,
                                                  @RequestBody ProductDTO request) {

        Product updatedProduct = productService.updateUserProduct(id, request);
        return AdminProductDTO.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .ownerName(updatedProduct.getOwner().getUsername())
                .build();
    }

    @DeleteMapping("/{id}/admin")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_ADMIN"})
    public String adminDeleteUserProduct(@PathVariable("id") Long id) {

        return productService.deleteUserProduct(id);
    }
}
