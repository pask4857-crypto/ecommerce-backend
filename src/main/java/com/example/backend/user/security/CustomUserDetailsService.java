package com.example.backend.user.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.user.entity.User;
import com.example.backend.user.entity.UserStatus;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email)
                        throws UsernameNotFoundException {

                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("使用者不存在"));

                boolean enabled = user.getStatus() == UserStatus.ACTIVE;

                return org.springframework.security.core.userdetails.User.builder()
                                .username(user.getEmail())
                                .password(user.getPasswordHash())
                                .authorities(
                                                new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                                .accountExpired(false)
                                .accountLocked(!enabled)
                                .credentialsExpired(false)
                                .disabled(!enabled)
                                .build();
        }
}
