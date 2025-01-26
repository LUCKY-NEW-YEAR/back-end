package com.lucky.newyear.model.request;


import com.lucky.newyear.entity.RecipeTest;
import com.lucky.newyear.model.Recipe;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RecipeTestPostReq {
    @Pattern(regexp = "^[\\S]{1,6}$", message = "닉네임은 공백을 제외한 1~6자 길이여야 합니다.")
    private final String nickname;

    @Size(min = 1, max = 2, message = "육수는 1개만 선택할 수 있습니다.")
    private final List<Long> yuksu;

    @Size(min = 1, max = 2, message = "주 재료는 1개 이상, 2개 이하로 선택해야 합니다.")
    private final List<Long> main;
    @Size(min = 1, max = 2, message = "부 재료는 1개 이상, 2개 이하로 선택해야 합니다.")
    private final List<Long> sub;
    @Size(max = 3, message = "고명 재료는 3개 이하로 선택해야 합니다.")
    private final List<Long> garnish;

    public RecipeTest toEntity(String newUUID) {
        Recipe recipe = Recipe.builder()
                .yuksu(this.yuksu)
                .main(this.main)
                .sub(this.sub)
                .garnish(this.garnish)
                .build();

        return RecipeTest.builder()
                .ownerUUID(newUUID)
                .nickname(this.nickname)
                .recipe(recipe)
                .build();
    }
}
