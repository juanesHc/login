package com.example.login.dto.login.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
