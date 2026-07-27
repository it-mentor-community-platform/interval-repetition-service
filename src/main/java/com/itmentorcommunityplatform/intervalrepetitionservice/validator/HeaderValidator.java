package com.itmentorcommunityplatform.intervalrepetitionservice.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class HeaderValidator {

    public static void validateNameHeader(String name) {
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: missing name header");
        }
    }

    public static void validateIdHeader(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: missing id header");
        }
    }

    public static void validateRoleHeader(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: missing role header");
        }
    }

}
