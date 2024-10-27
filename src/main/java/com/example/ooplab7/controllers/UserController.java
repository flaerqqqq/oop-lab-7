package com.example.ooplab7.controllers;

import com.example.ooplab7.dtos.UserDto;
import com.example.ooplab7.dtos.UserResponseDto;
import com.example.ooplab7.mappers.UserMapper;
import com.example.ooplab7.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserResponseDto> getAuthenticatedUserData(Principal principal) {
        UserDto userDto = userService.getUserByUsername(principal.getName());
        UserResponseDto response = userMapper.toResponseDto(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserResponseDto>> getUsers(Pageable pageable, Principal principal){
        List<UserDto> listOfUsers = userService.getUsers(pageable).stream()
                .filter(user -> !user.getUsername().equals(principal.getName()))
                .collect(Collectors.toList());
        Page<UserDto> pageOfUsers = new PageImpl<>(listOfUsers, pageable, listOfUsers.size());

        Page<UserResponseDto> responsePage = pageOfUsers.map(userMapper::toResponseDto);
        if (responsePage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(responsePage, HttpStatus.OK);
    }

    @PostMapping("/{uuid}/ban")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(CREATED)
    public void banUser(@PathVariable String uuid) {
        userService.banUser(uuid);
    }

    @PostMapping("/{uuid}/unban")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(NO_CONTENT)
    public void unbanUser(@PathVariable String uuid) {
        userService.unbanUser(uuid);
    }
}
