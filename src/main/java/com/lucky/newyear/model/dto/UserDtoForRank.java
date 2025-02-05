package com.lucky.newyear.model.dto;

import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.model.response.RecipeTestRankRes;
import com.lucky.newyear.utill.EncryptUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDtoForRank {
    private final String userUUID;
    private final String nickname;
    private final Integer score;

    public static UserDtoForRank of(RecipeTestRecord record, String key) {
        String nicknameDec = EncryptUtil.decrypt(record.getNicknameEnc(), key);

        return UserDtoForRank.builder()
                .userUUID(record.getId().getUserUUID())
                .nickname(nicknameDec)
                .score(record.getScore())
                .build();
    }
}
