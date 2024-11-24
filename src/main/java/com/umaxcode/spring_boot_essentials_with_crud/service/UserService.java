package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.UserDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Creates a new user based on the provided user data transfer object.
     *
     * @param request the {@link UserDTO} containing the details of the user to be created
     * @return the newly created {@link User}
     */
    User createUser(UserDTO request);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the {@link User} associated with the provided username
     * @throws UsernameNotFoundException if no user is found with the specified username
     */
    User findUserByUsername(String username) throws UsernameNotFoundException;
}
