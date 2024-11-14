package com.umaxcode.spring_boot_essentials_with_crud.service;

import com.umaxcode.spring_boot_essentials_with_crud.model.dto.UserRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser_ShouldReturnSavedUser() {
        UserRequestDTO request = new UserRequestDTO("maxwell", "password123");

        User user = new User("maxwell", "password123");
        user.setId(UUID.randomUUID().toString());

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.addUser(request);

        assertNotNull(result);
        assertEquals("maxwell", result.getUsername());
        assertEquals("password123", result.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findUserById_ShouldReturnUser_WhenUserExists() {
        String id = UUID.randomUUID().toString();
        User user = new User("maxwell", "password123");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.findUserById(id);

        assertNotNull(result);
        assertEquals("maxwell", result.getUsername());
        assertEquals("password123", result.getPassword());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void findUserById_ShouldThrowException_WhenUserNotFound() {

        String id = UUID.randomUUID().toString();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.findUserById(id));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void findAllUsers_ShouldReturnListOfUsers() {
        User user1 = new User("maxwell1", "password123");
        User user2 = new User("maxwell2", "password456");
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        String id = UUID.randomUUID().toString();
        User existingUser = new User("maxwell", "password123");
        User updatedUser = new User("new-maxwell", "new-password123");
        UserRequestDTO request = new UserRequestDTO("new-maxwell", "new-password123");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(id, request);

        assertNotNull(result);
        assertEquals("new-maxwell", result.getUsername());
        assertEquals("new-password123", result.getPassword());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void deleteUser_ShouldInvokeDelete_WhenUserExists() {
        String id = UUID.randomUUID().toString();
        User user = new User("maxwell", "password123");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteUser(id);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_ShouldThrowException_WhenUserNotFound() {
        String id = UUID.randomUUID().toString();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(id));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(0)).delete(any(User.class));
    }
}
