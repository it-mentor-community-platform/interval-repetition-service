package com.itmentorcommunityplatform.intervalrepetitionservice.dto;

import java.util.List;

public record UserDTO (

        String telegramUsername,

        Long telegramId,

        List<String> roles

){}
