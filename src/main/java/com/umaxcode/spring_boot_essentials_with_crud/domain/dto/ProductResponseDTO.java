package com.umaxcode.spring_boot_essentials_with_crud.domain.dto;

import lombok.Builder;

@Builder
public record ProductResponseDTO(
        String id,
        String name,
        String description
) {
}
