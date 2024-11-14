package com.umaxcode.spring_boot_essentials_with_crud.repository;

import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    ArrayList<User> users = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {
        if (!isAlreadySaved(user)) {
            user.setId(UUID.randomUUID().toString());
            users.add(user);
        }

        return user;
    }

    private boolean isAlreadySaved(User user) {
        return users.contains(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(User user) {
        users.remove(user);
    }
}
