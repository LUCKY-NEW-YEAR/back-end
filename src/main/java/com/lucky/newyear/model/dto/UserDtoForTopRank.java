package com.lucky.newyear.model.dto;

import com.lucky.newyear.entity.RecipeTestRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDtoForTopRank {
    private final String nickname;
    private final Integer score;

    public static UserDtoForTopRank of(RecipeTestRecord record) {
        return UserDtoForTopRank.builder()
                .nickname(record.getNickname())
                .score(record.getScore())
                .build();
    }
}