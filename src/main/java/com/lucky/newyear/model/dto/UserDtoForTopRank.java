package com.lucky.newyear.model.dto;

import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.utill.EncryptUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDtoForTopRank {
    private final String nickname;
    private final Integer score;

    public static UserDtoForTopRank of(RecipeTestRecord record, String key) {
        String nicknameDec = EncryptUtil.decrypt(record.getNicknameEnc(), key);

        return UserDtoForTopRank.builder()
                .nickname(nicknameDec)
                .score(record.getScore())
                .build();
    }
}