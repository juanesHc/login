package com.example.login.service.user;

import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.dto.user.response.RegisterPersonResponseDto;
import com.example.login.entity.PersonEntity;
import com.example.login.exception.RegisterUserException;
import com.example.login.mapper.user.PersonMapper;
import com.example.login.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserService {
    private static final Logger log = LoggerFactory.getLogger(RegisterUserService.class);

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public RegisterPersonResponseDto registerUser(RegisterPersonRequestDto registerPersonRequestDto){
        try {
            PersonEntity personEntity = personMapper.registerPersonRequestDtoToPersonEntity(registerPersonRequestDto);
            if(validateEmailUnique(registerPersonRequestDto.getEmail())){
                log.warn(registerPersonRequestDto.getEmail()+" ya esta en uso");
                throw new RegisterUserException("El email ya esta en uso");
            }
            personRepository.save(personEntity);

            return new RegisterPersonResponseDto("Usuario registrado de forma exitosa");
        }catch (Exception exception){
            log.error("ocurrio un error al registrar al usuario ",exception);
            throw new RegisterUserException("No se pudo registrar al usuario");
        }
    }

    private boolean validateEmailUnique(String email){
        return personRepository.existsByEmail(email);
    }


}
