package com.example.login.service.admin;

import com.example.login.dto.admin.request.RegisterWithRoleRequestDto;
import com.example.login.dto.admin.response.RegisterWithRoleResponseDto;
import com.example.login.entity.PersonEntity;
import com.example.login.exception.RegisterUserException;
import com.example.login.mapper.admin.AdminMapper;
import com.example.login.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterWithRoleService {

    private static final Logger log = LoggerFactory.getLogger(RegisterWithRoleService.class);

    private final PersonRepository personRepository;
    private final AdminMapper adminMapper;

    public RegisterWithRoleResponseDto registerWithRole(RegisterWithRoleRequestDto registerWithRoleRequestDto){
        try {
            PersonEntity personEntity = adminMapper. registerWithRoleRequestDtoToPersonEntity(registerWithRoleRequestDto);
            if(validateEmailUnique(registerWithRoleRequestDto.getEmail())){
                log.warn(registerWithRoleRequestDto.getEmail()+" ya esta en uso");
                throw new RegisterUserException("El email ya esta en uso");
            }

           personRepository.save(personEntity);


            return new RegisterWithRoleResponseDto("Usuario registrado de forma exitosa");
        }catch (Exception exception){
            log.error("ocurrio un error al registrar al usuario ",exception);
            throw new RegisterUserException("No se pudo registrar al usuario");
        }
    }

    private boolean validateEmailUnique(String email){
        return personRepository.existsByEmail(email);
    }


}
