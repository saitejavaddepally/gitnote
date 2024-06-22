package com.example.patterns.vo.auth;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse {
    private String error;
    private String message;
}
