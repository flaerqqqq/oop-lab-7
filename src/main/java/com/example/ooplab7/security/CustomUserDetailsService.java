package com.example.ooplab7.security;

import com.example.ooplab7.models.User;
import com.example.ooplab7.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with such username is not found: %s".formatted(username)));
        if (user.isDisabled()) {
            throw new RuntimeException("User with username is disabled: %s".formatted(username));
        }
        return new CustomUserDetails(user);
    }
}
