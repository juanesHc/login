package com.example.login.service.login;

import com.example.login.dto.login.request.LoginRequestDto;
import com.example.login.dto.login.response.AuthResponseDto;
import com.example.login.entity.PersonEntity;
import com.example.login.exception.LoginException;
import com.example.login.repository.person.PersonRepository;

import com.example.login.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final PersonRepository personRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        PersonEntity personEntity = personRepository.findByEmail(loginRequestDto.getEmail());

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), personEntity.getPassword())) {
            log.warn("Contraseña Incorrecta");
            throw new LoginException("Contraseña incorrecta");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setToken(jwtService.generateToken(userDetails,personEntity.getId()));

        return authResponseDto;
    }
}
