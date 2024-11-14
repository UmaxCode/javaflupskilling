package com.umaxcode.spring_boot_essentials_with_crud.repository;

import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void save_ShouldAddUser_WhenUserIsNotAlreadySaved() {
        User user = new User("maxwell", "password123");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("maxwell", savedUser.getUsername());
        assertEquals("password123", savedUser.getPassword());
        assertTrue(userRepository.findAll().contains(savedUser));
    }

    @Test
    void save_ShouldNotAddUser_WhenUserIsAlreadySaved() {
        User user = new User("maxwell", "password123");
        userRepository.save(user);

        int initialSize = userRepository.findAll().size();
        userRepository.save(user);

        assertEquals(initialSize, userRepository.findAll().size());
    }

    @Test
    void findById_ShouldReturnUser_WhenUserWithGivenIdExists() {
        User user = new User("maxwell", "password123");
        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(savedUser, foundUser.get());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenUserWithGivenIdDoesNotExist() {
        Optional<User> foundUser = userRepository.findById("non_existent_id");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllSavedUsers() {
        User user1 = new User("maxwell1", "password123");
        User user2 = new User("maxwell2", "password456");
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    void delete_ShouldRemoveUser_WhenUserExists() {
        User user = new User("maxwell", "password123");
        User savedUser = userRepository.save(user);

        userRepository.delete(savedUser);

        assertFalse(userRepository.findAll().contains(savedUser));
    }

    @Test
    void delete_ShouldDoNothing_WhenUserDoesNotExist() {
        User user = new User("non_existent_user", "password123");

        int initialSize = userRepository.findAll().size();
        userRepository.delete(user);

        assertEquals(initialSize, userRepository.findAll().size());
    }
}
