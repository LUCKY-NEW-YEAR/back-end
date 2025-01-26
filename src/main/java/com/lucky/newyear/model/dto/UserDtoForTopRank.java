package com.lucky.newyear.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDtoForTopRank {
    private final String nickname;
    private final Long score;
}