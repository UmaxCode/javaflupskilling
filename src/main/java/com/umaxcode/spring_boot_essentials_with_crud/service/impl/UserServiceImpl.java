package com.umaxcode.spring_boot_essentials_with_crud.service.impl;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.UserDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.Role;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.repository.UserRepository;
import com.umaxcode.spring_boot_essentials_with_crud.security.SecureUser;
import com.umaxcode.spring_boot_essentials_with_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} and {@link UserDetailsService}.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(UserDTO request) {

        User userInstance = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.REGULAR)
                .build();

        return userRepository.save(userInstance);
    }

    /**
     * Loads a user by their username and returns a {@link UserDetails} object.
     *
     * <p>This method is typically used by Spring Security to authenticate and authorize a user.
     * It retrieves the user details from the underlying data source,
     * wraps them in a {@link SecureUser}, and returns the result.</p>
     *
     * @param username the username of the user to be loaded
     * @return a {@link UserDetails} object representing the authenticated user
     * @throws UsernameNotFoundException if no user is found with the specified username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecureUser(findUserByUsername(username));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
