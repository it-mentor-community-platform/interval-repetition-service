package com.itmentorcommunityplatform.intervalrepetitionservice.validator;

import com.itmentorcommunityplatform.intervalrepetitionservice.exception.ValidationException;


public class HeaderValidator {

    private HeaderValidator() {}


    public static void validateIdHeader(Long id) {
        if (id == null) {
            throw new ValidationException("Access denied: missing id header");
        }
    }

}
