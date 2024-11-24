package com.umaxcode.spring_boot_essentials_with_crud.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long id;

    @Column(unique = true)
    private String name;

    private String description;
}