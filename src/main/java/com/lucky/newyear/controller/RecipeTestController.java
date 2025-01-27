package com.lucky.newyear.controller;

import com.lucky.newyear.model.request.RecipeTestGradeReq;
import com.lucky.newyear.model.response.*;
import com.lucky.newyear.model.request.RecipeTestPostReq;
import com.lucky.newyear.service.RecipeTestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Operation(summary = "레시피 시험지 존재 여부 확인", description = "")
    @GetMapping("/{ownerUUID}/exists")  //
    public ResponseEntity<RecipeTestExistRes> existRecipeTest(
            @PathVariable final String ownerUUID
    ) {
        RecipeTestExistRes response = recipeTestService.existRecipeTest(ownerUUID);

        return ResponseEntity.status(200).body(response);
    }


    @Operation(summary = "레시피 테스트 랭킹 목록 확인", description = "")
    @GetMapping("/{ownerUUID}/rank")  //
    public ResponseEntity<RecipeTestRankRes> getRecipeTestRanking(
            @PathVariable final String ownerUUID,
            @RequestParam(required = false, defaultValue = "1") final int page,  // 기본 페이지는 1
            @RequestParam(required = false, defaultValue = "5") final int size   // 기본 크기는 5
    ) {
        RecipeTestRankRes response = recipeTestService.getRecipeTestRanking(ownerUUID, page, size);

        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "레시피 테스트 랭킹 목록 확인", description = "")
    @GetMapping("/{ownerUUID}/detail")  //
    public ResponseEntity<RecipeTestGetDetailRes> getRecipeTestDetail(
            @PathVariable final String ownerUUID,
            @RequestParam final String findUUID
    ) {
        RecipeTestGetDetailRes response = recipeTestService.getRecipeTestDetail(ownerUUID, findUUID);

        return ResponseEntity.status(200).body(response);
    }
}
