package com.lucky.newyear.controller;

import com.lucky.newyear.model.request.RecipeTestGradeReq;
import com.lucky.newyear.model.response.RecipeTestGradeRes;
import com.lucky.newyear.model.request.RecipeTestPostReq;
import com.lucky.newyear.model.response.RecipeTestPostRes;
import com.lucky.newyear.service.RecipeTestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/recipe/test")
public class RecipeTestController {

    private final RecipeTestService recipeTestService;

    // 레시피 시험지 등록
    @Operation(summary = "레시피 시험지 등록", description = "")
    @PostMapping //
    public ResponseEntity<RecipeTestPostRes> postRecipeTest(
            @RequestBody @Valid RecipeTestPostReq recipeTestPostReq
    ) {
        RecipeTestPostRes response = recipeTestService.postRecipeTest(recipeTestPostReq);

        return ResponseEntity.status(200).body(response);
    }

    // 레시피 점수 채점
    @Operation(summary = "레시피 점수 채점", description = "")
    @PutMapping("/{ownerUUID}") //
    public ResponseEntity<RecipeTestGradeRes> gradeRecipeTest(
            @PathVariable final String ownerUUID,
            @RequestBody @Valid final RecipeTestGradeReq recipeTestGradeReq
    ) {
        RecipeTestGradeRes response = recipeTestService.gradeRecipeTest(ownerUUID, recipeTestGradeReq);

        return ResponseEntity.status(200).body(response);
    }
}
