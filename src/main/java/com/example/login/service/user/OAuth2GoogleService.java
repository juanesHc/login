package com.example.login.service.user;

import com.example.login.dto.login.response.AuthResponseDto;
import com.example.login.dto.user.request.OAuth2GoogleRequestDto;
import com.example.login.entity.PersonEntity;
import com.example.login.entity.enums.AuthEnum;
import com.example.login.exception.RegisterUserException;
import com.example.login.mapper.user.PersonMapper;
import com.example.login.repository.person.PersonRepository;
import com.example.login.security.model.SecurityUser;
import com.example.login.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2GoogleService {

    private final JwtService jwtService;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public AuthResponseDto registerByGoogleProvider(OAuth2GoogleRequestDto OAuth2GoogleRequestDto){
        PersonEntity personEntity = personRepository.findByEmail(OAuth2GoogleRequestDto.getEmail());

        if (personEntity==(null)) {
            return createGoogleUser(OAuth2GoogleRequestDto);
        }
        else if(personEntity.getProvider().equals(AuthEnum.LOCAL)){
            throw new RegisterUserException("Account Register with email and password");
        }
        else {
            return generateTokenGoogle(personEntity);
        }

    }

    private AuthResponseDto createGoogleUser(OAuth2GoogleRequestDto OAuth2GoogleRequestDto) {

        PersonEntity newUser = personMapper.registerGoogleRequestDtoToPersonEntity(OAuth2GoogleRequestDto);
        personRepository.save(newUser);

        return generateTokenGoogle(newUser);

    }


    private AuthResponseDto generateTokenGoogle(PersonEntity personEntity) {

        UserDetails userDetails = new SecurityUser(personEntity);

        String token = jwtService.generateToken(
                userDetails,
                personEntity.getId()
        );

        return new AuthResponseDto(token);
    }

}
