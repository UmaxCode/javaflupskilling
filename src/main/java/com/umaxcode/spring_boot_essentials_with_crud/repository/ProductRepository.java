package com.umaxcode.spring_boot_essentials_with_crud.repository;

import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(String id);

    List<Product> findAll();

    void delete(Product product);
}
