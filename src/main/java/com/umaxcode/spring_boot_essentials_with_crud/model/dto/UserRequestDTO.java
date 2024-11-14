package com.umaxcode.spring_boot_essentials_with_crud.model.dto;

public record UserRequestDTO(
        String username,
        String password
) {
}
