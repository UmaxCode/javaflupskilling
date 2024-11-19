package com.umaxcode.spring_boot_essentials_with_crud.repository;

import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the {@link ProductRepository}.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final List<Product> products;

    public ProductRepositoryImpl() {
        this.products = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product save(Product product) {
        if (!alreadyExists(product)) {
            product.setId(UUID.randomUUID().toString());
            this.products.add(product);
        }
        return product;
    }

    private boolean alreadyExists(Product product) {
        return this.products.contains(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> findById(String id) {
        return this.products.stream()
                .filter(product -> product.getId().equals(id)).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAll() {
        return this.products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Product product) {
        this.products.remove(product);
    }
}
