package com.lucky.newyear.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecipeTestExistRes {
    private final boolean isExists;
}
