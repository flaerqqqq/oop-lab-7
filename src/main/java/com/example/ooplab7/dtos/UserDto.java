package com.example.ooplab7.dtos;

import com.example.ooplab7.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String uuid;

    private String username;

    private String email;

    private String password;

    private LocalDateTime creationDate;

    private List<Role> roles;

    private boolean isDisabled;

}
