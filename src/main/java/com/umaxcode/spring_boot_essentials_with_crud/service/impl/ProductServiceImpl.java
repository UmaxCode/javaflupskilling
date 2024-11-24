package com.umaxcode.spring_boot_essentials_with_crud.service.impl;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.ProductDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Product;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.exception.ServiceException;
import com.umaxcode.spring_boot_essentials_with_crud.repository.ProductRepository;
import com.umaxcode.spring_boot_essentials_with_crud.service.ProductService;
import com.umaxcode.spring_boot_essentials_with_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProductService}.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with id = %d not found";
    private final ProductRepository productRepository;
    private final UserService userService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Product addProduct(ProductDTO request) {

        User user = userService.findUserByUsername(getAuthenticatedUsername());

        Product productInstance = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(user)
                .build();

        return productRepository.save(productInstance);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product findProductById(Long id) {

        Optional<Product> productOptional = productRepository.findByIdAndOwner_Username(id, getAuthenticatedUsername());
        if (productOptional.isPresent()) {
            return productOptional.get();
        }

        throw new ServiceException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAllProducts() {

        return productRepository.findAllByOwner_Username(getAuthenticatedUsername());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product updateProduct(Long id, ProductDTO request) {

        Product product = findProductById(id);
        product.setName(request.getName() != null ? request.getName() : product.getName());
        product.setDescription(request.getDescription() != null ? request.getDescription() : product.getDescription());
        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteProduct(Long id) {

        Product product = findProductById(id);
        productRepository.delete(product);
        return "Product with id = " + id + " is deleted successfully";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product getUserProduct(Long id) {
        return findUserProductById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAllUserProducts() {
        return productRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product updateUserProduct(Long id, ProductDTO request) {
        Product product = findUserProductById(id);
        product.setName(request.getName() != null ? request.getName() : product.getName());
        product.setDescription(request.getDescription() != null ? request.getDescription() : product.getDescription());
        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteUserProduct(Long id) {

        Product product = findUserProductById(id);
        productRepository.delete(product);
        return "Product with id = " + id + " is deleted successfully";
    }

    /**
     * Retrieves a product by its ID from the repository.
     *
     * <p>If the product is found, it is returned. Otherwise, a {@link ServiceException}
     * is thrown with an appropriate error message.</p>
     *
     * @param id the unique identifier of the product to retrieve
     * @return the {@link Product} associated with the given ID
     * @throws ServiceException if no product is found with the specified ID
     */
    private Product findUserProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }

        throw new ServiceException(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
    }

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * <p>This method fetches the authentication details from the {@link SecurityContextHolder}
     * and extracts the username from the authentication object.</p>
     *
     * @return the username of the currently authenticated user
     */
    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
