package com.example.login.service.admin;

import com.example.login.dto.role.RoleDto;
import com.example.login.exception.RetrieveRoleException;
import com.example.login.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetrieveRolesService {
    private final RoleRepository roleRepository;

    public RoleDto retrieveRoles(){
        try {
            RoleDto roleDtos = new RoleDto();
            roleDtos.setRoles(roleRepository.findAllRoleTypes());

            return roleDtos;
        }catch (RetrieveRoleException retrieveRoleException){
            log.error("It can retrieve roles",retrieveRoleException);
            throw new RetrieveRoleException("It can retrieve roles");
        }
    }
}
