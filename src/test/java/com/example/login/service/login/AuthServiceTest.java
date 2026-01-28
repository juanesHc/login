package com.example.login.service.login;

import com.example.login.dto.login.request.LoginRequestDto;
import com.example.login.dto.login.response.AuthResponseDto;
import com.example.login.entity.PersonEntity;
import com.example.login.entity.enums.RoleEnum;
import com.example.login.exception.LoginException;
import com.example.login.repository.person.PersonRepository;
import com.example.login.repository.role.RoleRepository;
import com.example.login.service.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.any;


import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private LoginRequestDto loginRequestDto;
    private PersonEntity personEntity;
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("juanes9@gmail.com");
        loginRequestDto.setPassword("Holamundo123$");

        personEntity = new PersonEntity();
        personEntity.setId(UUID.randomUUID());
        personEntity.setEmail("juanes9@gmail.com");
        personEntity.setPassword("Holamundo123$");
        personEntity.setAccountVerified(true);

        userDetails = User.builder()
                .username("juanest9@gmail.com")
                .password("$2a$10$encodedPassword")
                .authorities(Collections.emptyList())
                .build();
    }

    @Test
    void login_Success_ReturnsAuthResponseDto() {
        String expectedToken = "jwt.token.here";

        when(personRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(personEntity);
        when(passwordEncoder.matches(loginRequestDto.getPassword(), personEntity.getPassword())).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails, personEntity.getId(), personEntity.isAccountVerified()))
                .thenReturn(expectedToken);

        AuthResponseDto result = authService.login(loginRequestDto);

        assertNotNull(result);
        assertEquals(expectedToken, result.getToken());

        verify(personRepository).findByEmail(loginRequestDto.getEmail());
        verify(passwordEncoder).matches(loginRequestDto.getPassword(), personEntity.getPassword());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userDetails, personEntity.getId(), personEntity.isAccountVerified());
    }


    @Test
    void login_WrongPassword_ThrowsLoginException() {
        when(personRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(personEntity);
        when(passwordEncoder.matches(loginRequestDto.getPassword(), personEntity.getPassword())).thenReturn(false);

        LoginException exception = assertThrows(LoginException.class, () -> {
            authService.login(loginRequestDto);
        });

        assertEquals("Wrong password", exception.getMessage());

        verify(personRepository).findByEmail(loginRequestDto.getEmail());
        verify(passwordEncoder).matches(loginRequestDto.getPassword(), personEntity.getPassword());
        verify(authenticationManager, never()).authenticate(any());
        verify(jwtService, never()).generateToken(any(),any(UUID.class), anyBoolean());
    }

    @Test
    void login_UserNotFound_ThrowsNullPointerException() {
        when(personRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            authService.login(loginRequestDto);
        });

        verify(personRepository).findByEmail(loginRequestDto.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void login_AccountNotVerified_StillGeneratesToken() {
        personEntity.setAccountVerified(false);
        String expectedToken = "jwt.token.unverified";

        when(personRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(personEntity);
        when(passwordEncoder.matches(loginRequestDto.getPassword(), personEntity.getPassword())).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails, personEntity.getId(), false))
                .thenReturn(expectedToken);

        AuthResponseDto result = authService.login(loginRequestDto);

        assertNotNull(result);
        assertEquals(expectedToken, result.getToken());
        verify(jwtService).generateToken(userDetails, personEntity.getId(), false);
    }

    @Test
    void login_AuthenticationManagerThrowsException_PropagatesException() {
        // Arrange
        when(personRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(personEntity);
        when(passwordEncoder.matches(loginRequestDto.getPassword(), personEntity.getPassword())).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequestDto);
        });

        verify(jwtService, never()).generateToken(any(),any(UUID.class), anyBoolean());
    }

}
