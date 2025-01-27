package com.lucky.newyear.model.enums;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum IngredGarnishType {
    GYERAN(1, "계란"),
    PA(2, "파"),
    GIM(3, "김가루");

    private static final Map<Integer, IngredGarnishType> intToStringMap = new HashMap<>();

    static {
        // String -> Long 변환 테이블 생성
        for (IngredGarnishType type : values()) {
            intToStringMap.put(type.getId(), type);
        }
    }

    private final Integer id;
    private final String descript;

    public static final int SIZE = values().length;

    IngredGarnishType(Integer id, String descript) {
        this.id = id;
        this.descript = descript;
    }

    public static IngredGarnishType fromId(int id) {
        return intToStringMap.get(id);
    }

    public static List<IngredGarnishType> fromIds(List<Integer> idList) {
        return idList.stream().map(intToStringMap::get).toList();
    }

    public static Integer calculCarnishScore(List<IngredGarnishType> ownersList, List<IngredGarnishType> testersList) {
        List<Integer> countList = new ArrayList<>(Collections.nCopies(SIZE, 0));

        Stream.concat(ownersList.stream(), testersList.stream())
                .forEach(ingred -> {
                    int idx = ingred.getId()-1;
                    int value = countList.get(idx);

                    // 해당 값 증가시키기
                    countList.set(idx, value + 1);
                });

        boolean dup = false;
        int nonCount = 0;
        for (Integer count : countList)  {
            if (count == 2) {
                dup = true;
            } else if (count == 0) {
                nonCount ++;
            }
        }
        int score = 0;

        if (!dup) {
            if (nonCount == 0) {
                score += 20;
            } else if (nonCount == 1) {
                score += 10;
            }
        }
        if (nonCount == 3) {
            score -= 10;
        }

        return score;
    }
}
