package com.example.login.controller.admin;

import com.example.login.dto.admin.request.RegisterWithRoleRequestDto;
import com.example.login.dto.admin.response.RegisterWithRoleResponseDto;
import com.example.login.service.admin.RegisterWithRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final RegisterWithRoleService registerWithRoleService;

    @PostMapping("/register")
    public ResponseEntity<RegisterWithRoleResponseDto> postUser(@RequestBody RegisterWithRoleRequestDto registerWithRoleRequestDto){
        return ResponseEntity.ok(registerWithRoleService.registerWithRole(registerWithRoleRequestDto));
    }
}
