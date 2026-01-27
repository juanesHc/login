package com.example.login.controller.auth;

import com.example.login.dto.login.request.LoginRequestDto;
import com.example.login.dto.login.response.AuthResponseDto;
import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.dto.user.response.AccountVerifiedDto;
import com.example.login.dto.user.response.RegisterPersonResponseDto;
import com.example.login.service.login.AuthService;
import com.example.login.service.security.token.impl.email.VerifyUserService;
import com.example.login.service.user.RegisterUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RegisterUserService registerUserService;
    private final VerifyUserService verifyUserService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterPersonResponseDto> postUser(@Valid @RequestBody RegisterPersonRequestDto registerPersonRequestDto){
        return ResponseEntity.ok(registerUserService.registerUser(registerPersonRequestDto));
    }

    @GetMapping("/verify")
    public ResponseEntity<AccountVerifiedDto> verifyAccount(@RequestParam("token") String token, HttpServletResponse response) throws Exception {
            verifyUserService.verifyUser(token);
            return ResponseEntity.ok(
                    new AccountVerifiedDto("Account verified successfully")
            );
    }

    @GetMapping("/verify/resend")
    public ResponseEntity<AccountVerifiedDto> resendVerification(@RequestParam("email") String email, HttpServletResponse response) throws Exception {
        verifyUserService.resendRegistrationConfirmationEmail(email);
        return ResponseEntity.ok(
                new AccountVerifiedDto("Account verified successfully")
        );
    }
}
