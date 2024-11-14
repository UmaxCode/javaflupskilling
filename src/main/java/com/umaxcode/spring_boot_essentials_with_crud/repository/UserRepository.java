package com.umaxcode.spring_boot_essentials_with_crud.repository;


import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository {

     User save(User user);

     Optional<User> findById(String id);

     List<User> findAll();

     void delete(User user);
}
