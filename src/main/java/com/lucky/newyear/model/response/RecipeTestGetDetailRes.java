package com.lucky.newyear.model.response;

import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.model.Recipe;
import com.lucky.newyear.model.dto.UserDtoForTopRank;
import com.lucky.newyear.model.enums.IngredGarnishType;
import com.lucky.newyear.model.enums.IngredMainType;
import com.lucky.newyear.model.enums.IngredSubType;
import com.lucky.newyear.model.enums.IngredYuksuType;
import com.lucky.newyear.utill.EncryptUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeTestGetDetailRes {
    private final String ownerUUID;
    private final String userUUID;
    private final Integer score;
    private final String title;
    private final String content;
    private final String nickname;
    private final String message;

    private final List<String> yuksu;
    private final List<String> main;
    private final List<String> sub;
    private final List<String> garnish;


    public static RecipeTestGetDetailRes of(
            RecipeTestRecord record,
            String ownerUUID,
            String content,
            String key
    ) {

        List<String> stringYuksu = record.getRecipe().getYuksu().stream()
                .map(id -> IngredYuksuType.fromId(id).name())
                .toList();
        List<String> stringMain = record.getRecipe().getMain().stream()
                .map(id -> IngredMainType.fromId(id).name())
                .toList();
        List<String> stringSub = record.getRecipe().getSub().stream()
                .map(id -> IngredSubType.fromId(id).name())
                .toList();
        List<String> stringGarnish = record.getRecipe().getGarnish().stream()
                .map(id -> IngredGarnishType.fromId(id).name())
                .toList();

        String nicknameDec = EncryptUtil.decrypt(record.getNicknameEnc(), key);

        return RecipeTestGetDetailRes.builder()
                .ownerUUID(ownerUUID)
                .userUUID(record.getId().getUserUUID())
                .score(record.getScore())
                .title(record.getTitle())
                .nickname(nicknameDec)
                .message(record.getMessage())
                .yuksu(stringYuksu)
                .main(stringMain)
                .sub(stringSub)
                .garnish(stringGarnish)
                .content(content)
                .build();
    }
}
