package br.com.joao.authserviceapi.service;

import br.com.joao.authserviceapi.repositories.UserRepository;
import br.com.joao.authserviceapi.security.dtos.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        var entity = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return UserDetailsDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .userName(entity.getEmail())
                .password(entity.getPassword())
                .authorities(entity.getProfiles().stream().map(prof -> new SimpleGrantedAuthority(prof.getDescription())).collect(Collectors.toSet()))
                .build();
    }
}
