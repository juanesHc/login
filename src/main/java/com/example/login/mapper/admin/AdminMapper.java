package com.example.login.mapper.admin;

import com.example.login.dto.admin.request.RegisterWithRoleRequestDto;
import com.example.login.entity.PersonEntity;
import com.example.login.entity.RoleEntity;
import com.example.login.entity.enums.AuthEnum;
import com.example.login.entity.enums.RoleEnum;
import com.example.login.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {

    private final RoleRepository roleRepository;

    public PersonEntity registerWithRoleRequestDtoToPersonEntity(RegisterWithRoleRequestDto registerPersonRequestDto){
        PersonEntity personEntity=new PersonEntity();

        personEntity.setEmail(registerPersonRequestDto.getEmail());
        personEntity.setProvider(AuthEnum.LOCAL);
        personEntity.setPassword(registerPersonRequestDto.getPassword());
        personEntity.setGivenName(registerPersonRequestDto.getGivenName());
        personEntity.setFamilyName(registerPersonRequestDto.getFamilyName());

        RoleEntity roleEntity =
                roleRepository.findByType(RoleEnum.valueOf(registerPersonRequestDto.getRole()));

        personEntity.setRole(roleEntity);

        return personEntity;
    }
}
