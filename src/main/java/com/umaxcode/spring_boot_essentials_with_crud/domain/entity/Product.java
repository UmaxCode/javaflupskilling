package com.umaxcode.spring_boot_essentials_with_crud.domain.entity;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;

    private String name;

    private String description;
}
