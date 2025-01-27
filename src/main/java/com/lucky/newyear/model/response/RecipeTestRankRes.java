package com.lucky.newyear.model.response;

import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.model.dto.UserDtoForRank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class RecipeTestRankRes {
    private final List<UserDtoForRank> rankList;

    private final Integer page;
    private final Integer size;
    private final Integer totalPages;

    public static RecipeTestRankRes of(Page<RecipeTestRecord> recordPage) {
        List<UserDtoForRank> result = recordPage.getContent().stream()
                .map(UserDtoForRank::of)
                .toList();

        return RecipeTestRankRes.builder()
                .rankList(result)
                .page(recordPage.getNumber())
                .size(recordPage.getSize())
                .totalPages(recordPage.getTotalPages())
                .build();
    }
}
