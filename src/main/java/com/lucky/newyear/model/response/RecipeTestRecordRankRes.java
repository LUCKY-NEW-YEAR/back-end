package com.lucky.newyear.model.response;

import com.lucky.newyear.model.dto.UserDtoForRank;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeTestRecordRankRes {
    private final String shareUrl;

    private final List<UserDtoForRank> rankList;

    private final Long pageSize;
    private final Long pageNo;
}
