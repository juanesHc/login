package com.example.login.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OAuth2GoogleRequestDto {
    private String email;
    private String fullName;
}
