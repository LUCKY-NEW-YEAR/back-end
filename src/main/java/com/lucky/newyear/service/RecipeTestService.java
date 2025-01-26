package com.lucky.newyear.service;

import com.lucky.newyear.model.RecipeTestPostReq;
import com.lucky.newyear.model.RecipeTestPostRes;
import com.lucky.newyear.repository.RecipeTestRecordRepository;
import com.lucky.newyear.repository.RecipeTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeTestService {
    private final RecipeTestRepository recipeTestRepo;
    private final RecipeTestRecordRepository recipeTestRecordRepo;

    public RecipeTestPostRes postRecipeTest(RecipeTestPostReq recipeTestPostReq) {
        return RecipeTestPostRes.builder().build();
    }
}
