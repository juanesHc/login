package com.example.login.controller.admin;

import com.example.login.dto.role.RoleDto;
import com.example.login.service.admin.RetrieveRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class RoleController {
    private final RetrieveRolesService retrieveRoleService;

    @GetMapping("/retrieve/role")
    public ResponseEntity<RoleDto> getRole() {

        return ResponseEntity.ok(retrieveRoleService.retrieveRoles());
    }
}
