package com.example.login.service.user;

import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.dto.user.response.RegisterPersonResponseDto;
import com.example.login.entity.PersonEntity;
import com.example.login.exception.RegisterUserException;
import com.example.login.mapper.user.PersonMapper;
import com.example.login.repository.person.PersonRepository;
import com.example.login.service.security.token.impl.email.VerifyUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService {
    private static final Logger log = LoggerFactory.getLogger(RegisterUserService.class);

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final VerifyUserService verifyUserService;

    public RegisterPersonResponseDto registerUser(RegisterPersonRequestDto registerPersonRequestDto){

try {
    if (validateEmailUnique(registerPersonRequestDto.getEmail())) {
        log.warn(registerPersonRequestDto.getEmail(), " it is already in use");
        throw new RegisterUserException("Email it is already in use");
    }

    PersonEntity personEntity = personMapper.registerPersonRequestDtoToPersonEntity(registerPersonRequestDto);
    personEntity.setPassword(passwordEncoder.encode(personEntity.getPassword()));
    PersonEntity personSaved=personRepository.save(personEntity);
    verifyUserService.sendRegistrationConfirmationEmail(personSaved);

    return new RegisterPersonResponseDto("Register in a successful way!!");
}catch (RegisterUserException registerUserException){
    log.error("We got a issue register the user",registerUserException);
    throw new RegisterUserException("We got a issue register the user");
}
    }

    private boolean validateEmailUnique(String email){
        return personRepository.existsByEmail(email);
    }
}
