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
                log.warn(registerWithRoleRequestDto.getEmail()," Email it is already in use");
                throw new RegisterUserException("Email it is already in use");
            }

           personRepository.save(personEntity);


            return new RegisterWithRoleResponseDto("User register in successful way");
        }catch (Exception exception){
            log.error("We got a issue register the user",exception);
            throw new RegisterUserException("We got a issue register the user");
        }
    }

    private boolean validateEmailUnique(String email){
        return personRepository.existsByEmail(email);
    }


}
