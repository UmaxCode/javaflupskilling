package com.umaxcode.spring_boot_essentials_with_crud.repository;


import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * This interface provides basic CRUD operations for interacting with the user data store.
 */
public interface UserRepository {

     /**
      * Saves a {@link User} entity to the data store.
      * If the user does not already exist, it will be added; otherwise, an existing user will be updated.
      *
      * @param user the {@link User} entity to save or update
      * @return the saved {@link User} entity with any changes persisted
      */
     User save(User user);

     /**
      * Retrieves a {@link User} by its unique identifier.
      * If no user is found with the provided ID, an empty {@link Optional} is returned.
      *
      * @param id the unique identifier of the user to find
      * @return an {@link Optional} containing the {@link User} if found, or an empty {@link Optional} if not
      */
     Optional<User> findById(String id);

     /**
      * Retrieves all users from the data store.
      *
      * @return a {@link List} of all {@link User} entities
      */
     List<User> findAll();

     /**
      * Deletes the specified {@link User} entity from the data store.
      *
      * @param user the {@link User} entity to delete
      */
     void delete(User user);
}
