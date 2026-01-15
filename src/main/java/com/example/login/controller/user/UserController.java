package com.example.login.controller.user;

import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.dto.user.response.RegisterPersonResponseDto;
import com.example.login.service.user.RegisterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<RegisterPersonResponseDto> postUser(@RequestBody RegisterPersonRequestDto registerPersonRequestDto){

        RegisterPersonResponseDto response=registerUserService.registerUser(registerPersonRequestDto);

        return ResponseEntity.status(201).body(response);
    }
}
