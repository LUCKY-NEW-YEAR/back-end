package com.lucky.newyear.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDtoForRank {
    private final String userUUID;
    private final String nickname;
    private final Long score;
}
