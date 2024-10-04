package com.example.ooplab7.services.impls;

import com.example.ooplab7.dtos.UserRegisterRequest;
import com.example.ooplab7.dtos.UserResponseDto;
import com.example.ooplab7.exceptions.RegistrationException;
import com.example.ooplab7.mappers.UserMapper;
import com.example.ooplab7.models.Role;
import com.example.ooplab7.models.User;
import com.example.ooplab7.repositories.RoleRepository;
import com.example.ooplab7.repositories.UserRepository;
import com.example.ooplab7.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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


    @Override
    public UserResponseDto register(UserRegisterRequest request) {
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
}
