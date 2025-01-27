package com.lucky.newyear.model.response;

import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.model.Recipe;
import com.lucky.newyear.model.dto.UserDtoForTopRank;
import com.lucky.newyear.model.enums.IngredGarnishType;
import com.lucky.newyear.model.enums.IngredMainType;
import com.lucky.newyear.model.enums.IngredSubType;
import com.lucky.newyear.model.enums.IngredYuksuType;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeTestGradeRes {
    // 본인 점수 계산 + 랭킹 조회 + 되면 등수도 표시 ㄱ?
    private final Integer myScore;

    private final String title;

    private final String content;


    private final List<String> yuksu;
    private final List<String> main;
    private final List<String> sub;
    private final List<String> garnish;

    private final List<UserDtoForTopRank> topRankList;

    public static RecipeTestGradeRes of(
            final Integer myScore,
            final String title,
            final String content,
            final List<RecipeTestRecord> topRankList,
            final Recipe recipe
    ) {
        List<UserDtoForTopRank> topRankDtoList = topRankList.stream()
                .map(UserDtoForTopRank::of)
                .toList();


        List<String> stringYuksu = recipe.getYuksu().stream()
                .map(id -> IngredYuksuType.fromId(id).name())
                .toList();
        List<String> stringMain = recipe.getMain().stream()
                .map(id -> IngredMainType.fromId(id).name())
                .toList();
        List<String> stringSub = recipe.getSub().stream()
                .map(id -> IngredSubType.fromId(id).name())
                .toList();
        List<String> stringGarnish = recipe.getGarnish().stream()
                .map(id -> IngredGarnishType.fromId(id).name())
                .toList();

        return RecipeTestGradeRes.builder()
                .myScore(myScore)
                .title(title)
                .content(content)
                .yuksu(stringYuksu)
                .main(stringMain)
                .sub(stringSub)
                .garnish(stringGarnish)
                .topRankList(topRankDtoList)
                .build();
    }
}
