package com.example.login.repository.role;

import com.example.login.entity.RoleEntity;
import com.example.login.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    RoleEntity findByType(RoleEnum type);

    @Query("SELECT r.type FROM RoleEntity r")
    List<String> findAllRoleTypes();

}
