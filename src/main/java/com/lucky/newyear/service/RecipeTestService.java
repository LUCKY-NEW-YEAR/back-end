package com.lucky.newyear.service;

import com.lucky.newyear.config.UserContext;
import com.lucky.newyear.entity.RecipeTest;
import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.entity.compositeKey.RecipeTestRecordId;
import com.lucky.newyear.model.Recipe;
import com.lucky.newyear.model.enums.IngredGarnishType;
import com.lucky.newyear.model.enums.IngredMainType;
import com.lucky.newyear.model.enums.IngredSubType;
import com.lucky.newyear.model.enums.IngredYuksuType;
import com.lucky.newyear.model.request.RecipeTestGradeReq;
import com.lucky.newyear.model.response.RecipeTestExistRes;
import com.lucky.newyear.model.response.RecipeTestGradeRes;
import com.lucky.newyear.model.request.RecipeTestPostReq;
import com.lucky.newyear.model.response.RecipeTestPostRes;
import com.lucky.newyear.repository.RecipeTestRecordRepository;
import com.lucky.newyear.repository.RecipeTestRepository;
import com.lucky.newyear.utill.NyException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Internal;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeTestService {

    private final RecipeTestRepository recipeTestRepo;
    private final RecipeTestRecordRepository recipeTestRecordRepo;

    @Value("${newyear.share.url.base}")
    private String SHARE_URL_BASE;

    @Value("${newyear.tester.count.max}")
    private Long TESTER_COUNT_MAX;

    public RecipeTestPostRes postRecipeTest(RecipeTestPostReq recipeTestPostReq) {
        // user의 UUID 생성
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();

        // 그냥 입력사항 저장
        try {
            RecipeTest recipeTest = recipeTestPostReq.toEntity(uuidStr);

            recipeTestRepo.save(recipeTest);
        } catch (DataIntegrityViolationException e) {
            // unique constraint 위반 시 발생하는 예외 처리
            if (e.getCause() instanceof ConstraintViolationException) {
                // unique constraint 위반을 처리 -> 현재 100% UUID로 인해 발생.
                log.error("postRecipeTest() 극악의 확률로 식별값 생성에 실패 : uuidStr {}", e.getMessage());
                throw new NyException(HttpStatus.INTERNAL_SERVER_ERROR, "극악의 확률로 식별값 생성에 실패하였습니다. 로또 구매하러 가보셔도..");
            }
            throw new NyException(HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 오류입니다. 다시 시도 해주시기 바랍니다.");
        }

        String shareUrl = SHARE_URL_BASE + uuidStr;

        // UUID 발급 후. url 제작. 형식은 properties에서 받아오고.
        return RecipeTestPostRes.builder()
                .userUUID(uuidStr)
                .shareUrl(shareUrl)
                .build();
    }

    public RecipeTestExistRes existRecipeTest(String ownerUUID) {
        boolean isExists = recipeTestRepo.existsByOwnerUUID(ownerUUID);

        return RecipeTestExistRes.builder()
                .isExists(isExists)
                .build();
    }

    @Transactional
    public RecipeTestGradeRes gradeRecipeTest(String ownerUUID, RecipeTestGradeReq recipeTestGradeReq) {
        // 우선은 자기 자신 레시피에 접근해도 괜찮을듯.
        String reqUserUUID = UserContext.getUserUUID();

        RecipeTest recipeTest = recipeTestRepo.findByOwnerUUID(ownerUUID)
                .orElseThrow(() -> {
                    log.error("gradeRecipeTest() 없는 테스트로의 접근입니다. reqUserUUID : {}", reqUserUUID);
                    return new NyException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
                });


        // DB top Rank 조회
        try {
            // 최대 참가자 수 제한.
            if (recipeTest.getTesterCount() < TESTER_COUNT_MAX) {
                recipeTest.increaseTesterCount();
                recipeTestRepo.save(recipeTest);
            } else {
                log.error("gradeRecipeTest() 해당 테스트의 참가 인원이 초과되었습니다. recipeTestId : {}", recipeTest.getId());
                throw new NyException(HttpStatus.BAD_REQUEST, "해당 테스트에 참가할 수 있는 인원이 초과 되었습니다.");
            }
        } catch (OptimisticLockingFailureException e) {
            log.warn("gradeRecipeTest() tester count 충돌이 일어났습니다. recipeTestId : {}", recipeTest.getId());
            throw new NyException(HttpStatus.CONFLICT, "서버 과부하로 잠시 후 다시 시도해 주세요.");
        }


        // 점수 계산 로직
        Integer score = calculateScore(
                recipeTest.getRecipe(),
                recipeTestGradeReq.toRecipe()
        );

        // DB 저장
        RecipeTestRecordId id = new RecipeTestRecordId(
                recipeTest.getId(),
                reqUserUUID
        );

        Recipe mergeRecipe = Recipe.merge(
                recipeTest.getRecipe(),
                recipeTestGradeReq.toRecipe()
        );

        RecipeTestRecord recipeTestRecord = RecipeTestRecord.builder()
                .id(id)
                .recipeTest(recipeTest)
                .score(score)
                .recipe(mergeRecipe)
                .nickname(recipeTestGradeReq.getNickname())
                .message(recipeTestGradeReq.getMessage())
                .build();

        recipeTestRecordRepo.save(recipeTestRecord);

        // 점수에 맞는 문구 출력.
        String content = getContent(score);

        // 랭킹(Top 4) 리스트 조회
        List<RecipeTestRecord> topRankList = recipeTestRecordRepo.findTop4ByIdTestIdOrderByScoreDesc(recipeTest.getId());

        return RecipeTestGradeRes.of(
                score,
                content,
                topRankList
        );
    }

    private Integer calculateScore(Recipe ownerRecipe, Recipe testerRecipe) {
        Integer score = IngredYuksuType.calculYuksuScore(
                IngredYuksuType.firstFromIds(ownerRecipe.getYuksu()),
                IngredYuksuType.firstFromIds(testerRecipe.getYuksu())
        );

        score += IngredMainType.calculMainScore(
                IngredMainType.fromIds(ownerRecipe.getMain()),
                IngredMainType.fromIds(testerRecipe.getMain())
        );

        score += IngredSubType.calculSubScore(
                IngredSubType.fromIds(ownerRecipe.getSub()),
                IngredSubType.fromIds(testerRecipe.getSub())
        );

        score += IngredGarnishType.calculCarnishScore(
                IngredGarnishType.fromIds(ownerRecipe.getGarnish()),
                IngredGarnishType.fromIds(testerRecipe.getGarnish())
        );

        return score;
    }

    private String getContent(Integer score) {
        if (score >= 100) {
            return "히든 떡국 발견 ! 서로가 놓치지 말아야 할 귀인입니다.";
        } else if (score >= 90) {
            return "천생연분 ! 올해도 두 분이 함께라면 모든 일이 잘 풀릴 거예요.";
        } else if (score >= 60) {
            return "맛있는 떡국 완성 ! 올해에도 지금처럼 좋은 관계를 유지해 보세요.";
        } else if (score >= 30) {
            return "무난한 떡국을 끓였네요. 깊은 맛을 내려면 정성이 필요해요.";
        } else if (score >= 0) {
            return "떡국 간이 부족해요. 심심한 관계에 새로운 자극이 필요해요.";
        } else {
            log.error("점수가 0 점 미만이 되었습니다.");
            throw new NyException(HttpStatus.BAD_REQUEST, "점수가 0점 미만이 될 수는 없을텐데..");
        }
    }
}
