package com.umaxcode.spring_boot_essentials_with_crud.controller;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductResponseDTO;
import com.umaxcode.spring_boot_essentials_with_crud.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO addProduct(@RequestBody ProductRequestDTO request) {
        return productService.addProduct(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO getProduct(@PathVariable("id") String id) {
        return productService.findProductById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDTO> getAllProducts() {
        return productService.findAllProducts();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO updateProduct(@PathVariable("id") String id,
                                            @RequestBody ProductRequestDTO request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteProduct(@PathVariable("id") String id) {
        return productService.deleteProduct(id);
    }

}
