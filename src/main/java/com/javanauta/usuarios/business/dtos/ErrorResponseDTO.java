package com.javanauta.usuarios.business.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {
    private final LocalDateTime timestamp;
    private int httpStatus;
    private String error;
    private String message;
    private String path;
}
