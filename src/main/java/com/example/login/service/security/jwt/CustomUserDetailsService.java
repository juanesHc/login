package com.example.login.service.security.jwt;

import com.example.login.entity.PersonEntity;
import com.example.login.repository.person.PersonRepository;
import com.example.login.entity.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        PersonEntity personEntity =
                personRepository.findByEmail(email);
        if(personEntity.equals(null)){
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new SecurityUser(personEntity);
    }
}
