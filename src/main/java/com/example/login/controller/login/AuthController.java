package com.example.login.controller.login;

import com.example.login.dto.login.request.LoginRequestDto;
import com.example.login.dto.login.response.AuthResponseDto;
import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.dto.user.response.RegisterPersonResponseDto;
import com.example.login.service.login.AuthService;
import com.example.login.service.user.RegisterUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RegisterUserService registerUserService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterPersonResponseDto> postUser(@Valid @RequestBody RegisterPersonRequestDto registerPersonRequestDto){
        return ResponseEntity.ok(registerUserService.registerUser(registerPersonRequestDto));


    }
}
