package com.itmentorcommunityplatform.intervalrepetitionservice.validator;

import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ValidationException;


import java.util.List;

public class HeaderValidator {

    private HeaderValidator() {}

    public static void validateNameHeader(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Access denied: missing name header");
        }
    }

    public static void validateIdHeader(Long id) {
        if (id == null) {
            throw new ValidationException("Access denied: missing id header");
        }
    }

    public static void validateRoleHeader(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            throw new ValidationException("Access denied: missing role header");
        }
    }

}
