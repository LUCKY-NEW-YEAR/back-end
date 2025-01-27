package com.lucky.newyear.model.dto;

import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.model.response.RecipeTestRankRes;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDtoForRank {
    private final Long userId;
    private final String nickname;
    private final Integer score;

    public static UserDtoForRank of(RecipeTestRecord record) {
        return UserDtoForRank.builder()
                .userId(record.getId().getUserId())
                .nickname(record.getNickname())
                .score(record.getScore())
                .build();
    }
}
