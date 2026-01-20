package com.example.login.repository.person;

import com.example.login.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> , JpaSpecificationExecutor<PersonEntity> {

    boolean existsByEmail(String email);

    PersonEntity findByEmail(String email);

    @Query("SELECT p.id FROM PersonEntity p WHERE p.email = :email")
    UUID findIdByEmail(@Param("email") String email);
}
