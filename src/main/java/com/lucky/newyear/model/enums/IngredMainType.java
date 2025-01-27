package com.lucky.newyear.model.enums;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

@Getter
public enum IngredMainType {
    GARAE_TTEOK("가래떡", 0, 0),
    WHOLE_TTEOK("통가래떡", 0, 0),
    JORAENGI_TTEOK("조랭이떡", 0, 0),
    HONEY_TTEOK("꿀떡", 0, 1),
    KIMCHI_MANDU("김치만두", 1, 0),
    MEAT_MANDU("고기만두", 1, 0),
    GULRIM_MANDU("굴림만두", 1, 2);

    private final String descript;
    private final Integer combi1;   // 떡 3개 || 만두 3개 들어갈 경우 감점.
    private final Integer combi2;   // 꿀떡 & 굴림만두 조합

    IngredMainType(String descript, Integer combi1, Integer combi2) {
        this.descript = descript;
        this.combi1 = combi1;
        this.combi2 = combi2;
    }

    public static IngredMainType getIngredientByDescript(String descript) {
        for (IngredMainType ingredient : values()) {
            if (ingredient.descript.equals(descript)) {
                return ingredient;
            }
        }
        return null;
    }

    public static Integer calculMainScore(List<IngredMainType> ownersList, List<IngredMainType> testersList) {
        final int COMBI1_SIZE = 2;

        List<Long> combi1Count = new ArrayList<>(Collections.nCopies(COMBI1_SIZE, 0L));
        Set<Integer> combi2Set = new HashSet<>();
        int score = 0;

        Stream.concat(ownersList.stream(), testersList.stream())
                .forEach(ingred -> {
                    int com1Idx = ingred.getCombi1();
                    long value = combi1Count.get(com1Idx);

                    // 해당 값 증가시키기
                    combi1Count.set(com1Idx, value + 1);
                    combi2Set.add(ingred.getCombi2());
                });

        if (combi2Set.contains(1)
                && combi2Set.contains(2)) {
            score += 10;
        }

        if (combi1Count.get(0) >= 3) {
            score -= 10;
        }
        if (combi1Count.get(1) >= 3) {
            score -= 10;
        }

        return score;
    }
}
