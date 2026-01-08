package com.crois.course.service.jwt;


import com.crois.course.dto.UserDTO.AuthUser;
import com.crois.course.entity.UserEntity;
import com.crois.course.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        return AuthUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword().getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRoles().toString())))

                .enabled(user.isEnabled())
                .build();
    }
}
