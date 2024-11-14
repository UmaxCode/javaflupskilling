package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.model.dto.UserRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer class for managing user-related operations.
 * This service provides functionality to add, retrieve, update, and delete user records.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * inject UserRepository using constructor injection
     *
     * @param userRepository the repository used to interact with user data storage
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates and saves a new user using the details provided in the request.
     *
     * @param request the data transfer object containing user details (username and password)
     * @return the saved {@link User} object containing the generated ID and provided details
     */
    public User addUser(UserRequestDTO request) {
        User user = new User(request.username(), request.password());
        return this.userRepository.save(user);
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the unique identifier of the user to retrieve
     * @return the {@link User} object if found
     * @throws IllegalArgumentException if no user is found with the specified ID
     */
    public User findUserById(String id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new IllegalArgumentException("User not found");
    }

    /**
     * Retrieves all users stored in the repository.
     *
     * @return a list of all {@link User} objects available in the repository
     */
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Updates an existing user's information with the details provided in the request.
     * If a field in the request is null, the corresponding user field remains unchanged.
     *
     * @param id      the unique identifier of the user to update
     * @param request the data transfer object containing updated user details
     * @return the updated {@link User} object with the new information
     */
    public User updateUser(String id, UserRequestDTO request) {
        User user = this.findUserById(id);
        user.setUsername(request.username() != null ? request.username() : user.getUsername());
        user.setPassword(request.password() != null ? request.password() : user.getPassword());
        return this.userRepository.save(user);
    }

    /**
     * Deletes a user with the specified unique ID.
     *
     * @param id the unique identifier of the user to delete
     * @throws IllegalArgumentException if no user is found with the specified ID
     */
    public void deleteUser(String id) {
        User user = this.findUserById(id);
        this.userRepository.delete(user);
    }

}
