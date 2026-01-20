package com.example.login.service.admin;

import com.example.login.dto.role.RoleDto;
import com.example.login.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveRolesService {
    private final RoleRepository roleRepository;

    public RoleDto retrieveRoles(){
        RoleDto roleDtos=new RoleDto();
        roleDtos.setRoles(roleRepository.findAllRoleTypes());

        return roleDtos;
    }
}
