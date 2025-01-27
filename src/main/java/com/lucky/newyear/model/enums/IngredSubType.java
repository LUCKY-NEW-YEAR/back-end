package com.lucky.newyear.model.enums;

import com.lucky.newyear.utill.NyException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public enum IngredSubType {
    GARAE_TTEOK("굴", 0, 2),
    WHOLE_TTEOK("미역", 0, 4),
    JORAENGI_TTEOK("소고기", 0, 1),
    HONEY_TTEOK("닭고기", 0, 3),
    KIMCHI_MANDU("새우", 0, 5),
    MEAT_MANDU("꼬지", 1, 6),
    GULRIM_MANDU("탕후루", 2, 7);

    private final String coreName;
    private final Integer combi1;   // 꼬지, 탕후루 존재 시 감점, 모두 존재시 상점
    private final Integer priority;  // 네이밍 우선순위

    public static final int SIZE = values().length;

    IngredSubType(String coreName, Integer combi1, Integer priority) {
        this.coreName = coreName;
        this.combi1 = combi1;
        this.priority = priority;
    }

    public static IngredSubType getIngredientByDescript(String descript) {
        for (IngredSubType ingredient : values()) {
            if (ingredient.coreName.equals(descript)) {
                return ingredient;
            }
        }
        return null;
    }

    public static Integer calculSubScore(List<IngredSubType> ownersList, List<IngredSubType> testersList) {
        Set<Integer> combi1Set = Stream.concat(ownersList.stream(), testersList.stream())
                .map(IngredSubType::getCombi1)
                .collect(Collectors.toSet());

        int score = 0;

        boolean check1 = combi1Set.contains(1);
        boolean check2 = combi1Set.contains(2);

        if (check1 && check2) {
            score += 10;
        } else if (check1 || check2) {
            score -= 10;
        }

        return score;
    }

    public static String getSubName(List<IngredSubType> ownersList, List<IngredSubType> testersList) {
        List<Long> freqList = new ArrayList<>(Collections.nCopies(SIZE, 0L));

        Stream.concat(ownersList.stream(), testersList.stream())
                .forEach(ingred -> {
                    int com1Idx = ingred.getCombi1();
                    long value = freqList.get(com1Idx);

                    // 해당 값 증가시키기
                    freqList.set(com1Idx, value + 1);
                });

        int maxIndex = IntStream.range(0, freqList.size())
                .boxed()
                .max(Comparator.comparing(freqList::get))
                .orElse(0);

        return IngredSubType.values()[maxIndex].getCoreName();
    }
}
