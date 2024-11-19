package com.umaxcode.spring_boot_essentials_with_crud.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ExceptionResponse {
    private String path;
    private String message;
    private LocalDateTime timestamp;
}
