package com.attornatus.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorMessage {

    private HttpStatus status;
    private String message;
}
