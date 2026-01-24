package com.example.login.mapper.user;

import com.example.login.dto.user.request.OAuth2GoogleRequestDto;
import com.example.login.dto.user.request.RegisterPersonRequestDto;
import com.example.login.entity.PersonEntity;
import com.example.login.entity.RoleEntity;
import com.example.login.entity.enums.AuthEnum;
import com.example.login.entity.enums.RoleEnum;
import com.example.login.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    private final RoleRepository roleRepository;

    public PersonEntity registerPersonRequestDtoToPersonEntity(RegisterPersonRequestDto registerPersonRequestDto){
        PersonEntity personEntity=new PersonEntity();

        personEntity.setEmail(registerPersonRequestDto.getEmail());
        personEntity.setProvider(AuthEnum.LOCAL);
        personEntity.setPassword(registerPersonRequestDto.getPassword());
        personEntity.setGivenName(registerPersonRequestDto.getGivenName());
        personEntity.setFamilyName(registerPersonRequestDto.getFamilyName());

        personEntity.setAccountVerified(false);
        personEntity.setPhone("+57"+registerPersonRequestDto.getPhone());

        RoleEntity roleEntity =
                roleRepository.findByType(RoleEnum.USER);

        personEntity.setRole(roleEntity);

        return personEntity;
    }

    public PersonEntity registerGoogleRequestDtoToPersonEntity(OAuth2GoogleRequestDto OAuth2GoogleRequestDto){
        PersonEntity personEntity=new PersonEntity();

        String[] nameAsArray= OAuth2GoogleRequestDto.getFullName().split(" ");

        personEntity.setEmail(OAuth2GoogleRequestDto.getEmail());
        personEntity.setProvider(AuthEnum.GOOGLE);
        personEntity.setAccountVerified(true);

        personEntity.setGivenName(nameAsArray[0]);
        personEntity.setFamilyName(nameAsArray[1]);

        RoleEntity roleEntity =
                roleRepository.findByType(RoleEnum.USER);

        personEntity.setRole(roleEntity);

        return personEntity;
    }
}
