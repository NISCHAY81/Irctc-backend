package com.substring.irctc.dto;

import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private  Long id;
    private  String name;
    private  String password;
    private  String email;
    private String phone;
    private LocalDateTime createdAt;
    private List<RoleDto> roles = new ArrayList<>();
}
