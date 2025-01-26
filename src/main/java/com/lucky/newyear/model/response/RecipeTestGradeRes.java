package com.lucky.newyear.model.response;

import com.lucky.newyear.model.dto.UserDtoForTopRank;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeTestGradeRes {
    // 본인 점수 계산 + 랭킹 조회 + 되면 등수도 표시 ㄱ?
    private final String shareUrl;

    private final Integer myScore;
    private final Integer myRank;

    private final String content;

    private final List<UserDtoForTopRank> topRankList;
}
