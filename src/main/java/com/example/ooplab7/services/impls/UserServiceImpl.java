package com.example.ooplab7.services.impls;

import com.example.ooplab7.dtos.UserDto;
import com.example.ooplab7.exceptions.UserNotFoundException;
import com.example.ooplab7.mappers.UserMapper;
import com.example.ooplab7.models.User;
import com.example.ooplab7.repositories.UserRepository;
import com.example.ooplab7.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with such username is not found: %s".formatted(username)));

        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public void banUser(String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() ->
                new UserNotFoundException("User with such uuid is not found: %s".formatted(uuid)));
        if (user.isDisabled()) {
            throw new RuntimeException("User is already banned");
        }

        user.setDisabled(true);
        userRepository.save(user);
    }

    @Override
    public void unbanUser(String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() ->
                new UserNotFoundException("User with such uuid is not found: %s".formatted(uuid)));
        if (!user.isDisabled()) {
            throw new RuntimeException("User is not already banned");
        }

        user.setDisabled(false);
        userRepository.save(user);
    }
}
