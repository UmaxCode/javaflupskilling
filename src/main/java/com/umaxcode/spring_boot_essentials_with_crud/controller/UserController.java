package com.umaxcode.spring_boot_essentials_with_crud.controller;

import com.umaxcode.spring_boot_essentials_with_crud.domain.dto.UserDTO;
import com.umaxcode.spring_boot_essentials_with_crud.domain.entity.User;
import com.umaxcode.spring_boot_essentials_with_crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO signup(@RequestBody UserDTO request) {

        User savedUserResponse = userService.createUser(request);

        return UserDTO.builder()
                .id(savedUserResponse.getId())
                .username(savedUserResponse.getUsername())
                .build();
    }
}
