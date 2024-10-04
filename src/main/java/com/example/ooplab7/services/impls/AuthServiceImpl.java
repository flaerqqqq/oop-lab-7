package com.example.ooplab7.services.impls;

import com.example.ooplab7.dtos.JwtResponseDto;
import com.example.ooplab7.dtos.UserLoginRequestDto;
import com.example.ooplab7.dtos.UserRegisterRequestDto;
import com.example.ooplab7.dtos.UserResponseDto;
import com.example.ooplab7.exceptions.LoginException;
import com.example.ooplab7.exceptions.RegistrationException;
import com.example.ooplab7.exceptions.UserNotFoundException;
import com.example.ooplab7.mappers.UserMapper;
import com.example.ooplab7.models.Role;
import com.example.ooplab7.models.User;
import com.example.ooplab7.repositories.RoleRepository;
import com.example.ooplab7.repositories.UserRepository;
import com.example.ooplab7.security.CustomUserDetails;
import com.example.ooplab7.services.AuthService;
import com.example.ooplab7.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;


    @Override
    public UserResponseDto register(UserRegisterRequestDto request) {
        String username = request.getUsername();
        String email = request.getEmail();
        if (userRepository.existsByUsername(username)) {
            throw new RegistrationException("Such username already in use: %s".formatted(username));
        } else if (userRepository.existsByEmail(email)) {
            throw new RegistrationException("Such email already in use: %s".formatted(email));
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);
        User user = modelMapper.map(request, User.class);
        user.setUuid(UUID.randomUUID().toString());

        user.setRoles(List.of(roleRepository.findByName(Role.RoleName.USER).get()));

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public JwtResponseDto login(UserLoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() ->
                new UserNotFoundException("User with such username is not found: %s".formatted(request.getUsername())));

        authenticate(request);

        UserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generate(userDetails);

        return new JwtResponseDto(token);
    }

    private void authenticate(UserLoginRequestDto request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authentication = authManager.authenticate(authToken);

        if (!authentication.isAuthenticated()) {
            throw new LoginException("Incorrect password while logging in for user with username: %s"
                    .formatted(request.getUsername()));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
