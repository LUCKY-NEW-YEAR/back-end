package com.lucky.newyear.model.response;

import com.lucky.newyear.entity.RecipeTestRecord;
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

    private final String content;

    private final List<UserDtoForTopRank> topRankList;

    public static RecipeTestGradeRes of(
            Integer myScore,
            String content,
            List<RecipeTestRecord> topRankList
    ) {
        List<UserDtoForTopRank> topRankDtoList = topRankList.stream()
                .map(UserDtoForTopRank::of)
                .toList();

        return RecipeTestGradeRes.builder()
                .myScore(myScore)
                .content(content)
                .topRankList(topRankDtoList)
                .build();
    }
}
