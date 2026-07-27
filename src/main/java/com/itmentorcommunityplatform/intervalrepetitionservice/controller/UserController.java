package com.itmentorcommunityplatform.intervalrepetitionservice.controller;

import com.itmentorcommunityplatform.intervalrepetitionservice.dto.UserDTO;
import com.itmentorcommunityplatform.intervalrepetitionservice.validator.HeaderValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/interval-repetition/user-auth")
public class UserController {

    @GetMapping
    public ResponseEntity<UserDTO> getUser(
            @RequestHeader(value = "X-Telegram-Username", required = false) String name,
            @RequestHeader(value = "X-Telegram-User-Id", required = false) Long id,
            @RequestHeader(value = "X-User-Roles", required = false) List<String> roles) {

        HeaderValidator.validateNameHeader(name);
        HeaderValidator.validateIdHeader(id);
        HeaderValidator.validateRoleHeader(roles);

        return ResponseEntity.ok(new UserDTO(name, id, roles));

    }


}
