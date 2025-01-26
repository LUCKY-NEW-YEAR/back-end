package com.lucky.newyear.model.enums;

import java.util.*;
import java.util.stream.Stream;

public enum IngredMainType {
    GARAE_TTEOK("가래떡", 0, 0),
    WHOLE_TTEOK("통가래떡", 0, 0),
    JORAENGI_TTEOK("조랭이떡", 0, 0),
    HONEY_TTEOK("꿀떡", 0, 1),
    KIMCHI_MANDU("김치만두", 1, 0),
    MEAT_MANDU("고기만두", 1, 0),
    GULRIM_MANDU("굴림만두", 1, 2);

    private final String descript;
    private final Integer combi1;
    private final Integer combi2;

    IngredMainType(String descript, Integer combi1, Integer combi2) {
        this.descript = descript;
        this.combi1 = combi1;
        this.combi2 = combi2;
    }

    public Integer getCombi1() {
        return this.combi1;
    }

    public Integer getCombi2() {
        return this.combi2;
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
        Map<Integer, Integer> count = new HashMap<>();
        Set<Integer> combi2Set = new HashSet<>();
        int score = 0;

        Stream.concat(ownersList.stream(), testersList.stream())
                .forEach(ingred -> {
                    count.merge(ingred.getCombi1(), 1, Integer::sum);
                    combi2Set.add(ingred.getCombi2());
                });

        if (combi2Set.contains(1)
            && combi2Set.contains(2)) {
            score += 10;
        }

        if (count.getOrDefault(0, 0) >= 3) {
            score -= 10;
        }
        if (count.getOrDefault(1, 0) >= 3) {
            score -= 10;
        }

        return score;
    }
}
