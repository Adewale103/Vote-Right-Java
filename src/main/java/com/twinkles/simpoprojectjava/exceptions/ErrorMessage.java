package com.twinkles.simpoprojectjava.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorMessage {
    private String message;
    private LocalDateTime timestamp;
    private boolean status;
}
