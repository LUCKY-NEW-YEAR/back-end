package com.lucky.newyear.controller;

import com.lucky.newyear.model.RecipeTestPostReq;
import com.lucky.newyear.model.RecipeTestPostRes;
import com.lucky.newyear.service.RecipeTestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recipe/test")
public class RecipeTestController {

    private final RecipeTestService recipeTestService;

    //@Operation(summary = "", description = "")
    @PostMapping //
    public ResponseEntity<RecipeTestPostRes> postRecipeTest(
            @RequestBody @Valid RecipeTestPostReq recipeTestPostReq
    ) {
        RecipeTestPostRes response = recipeTestService.postRecipeTest(recipeTestPostReq);

        return ResponseEntity.status(200).body(response);
    }
}
