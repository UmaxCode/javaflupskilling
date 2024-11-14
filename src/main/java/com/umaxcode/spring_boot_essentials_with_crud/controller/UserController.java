package com.umaxcode.spring_boot_essentials_with_crud.controller;

import com.umaxcode.spring_boot_essentials_with_crud.model.dto.UserRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.addUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userService.findAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(this.userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        this.userService.deleteUser(id);
        return ResponseEntity.ok("Deleted user with id = " + id);
    }
}
