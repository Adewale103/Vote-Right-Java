package com.twinkles.simpoprojectjava.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateBVNResponse {
    private String status;
    private String message;
    private Data data;
}
