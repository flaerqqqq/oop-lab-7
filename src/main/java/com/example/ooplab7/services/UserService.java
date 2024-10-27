package com.example.ooplab7.services;

import com.example.ooplab7.dtos.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto getUserByUsername(String username);

    Page<UserDto> getUsers(Pageable pageable);

    void banUser(String uuid);

    void unbanUser(String uuid);
}
