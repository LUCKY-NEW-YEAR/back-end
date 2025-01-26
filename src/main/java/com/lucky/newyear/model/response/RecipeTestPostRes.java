package com.lucky.newyear.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeTestPostRes {
    private final String userUUID;
    private final String shareUrl;  // 본 url로 본인이 접속하면, 결과 화면으로 이동.
}
