package com.lucky.newyear.model.enums;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public enum IngredSubType {
    GUL(1, "굴", 0, 2),
    MIYEOK(2, "미역", 0, 4),
    SOGOGI(3, "소고기", 0, 1),
    DAK(4, "닭고기", 0, 3),
    SAEWOO(5, "새우", 0, 5),
    GGOJI(6, "꼬지", 1, 6),
    HURU(7, "탕후루", 2, 7);

    private static final Map<Integer, IngredSubType> intToStringMap = new HashMap<>();

    static {
        // String -> Long 변환 테이블 생성
        for (IngredSubType type : values()) {
            intToStringMap.put(type.getId(), type);
        }
    }

    private final Integer id;       // id 필드 맨 앞에 배치
    private final String coreName;
    private final Integer combi1;   // 꼬지, 탕후루 존재 시 감점, 모두 존재시 상점
    private final Integer priority;  // 네이밍 우선순위

    public static final int SIZE = values().length;

    IngredSubType(Integer id, String coreName, Integer combi1, Integer priority) {
        this.id = id;
        this.coreName = coreName;
        this.combi1 = combi1;
        this.priority = priority;
    }

    public static IngredSubType fromId(int id) {
        return intToStringMap.get(id);
    }

    public static List<IngredSubType> fromIds(List<Integer> idList) {
        return idList.stream().map(intToStringMap::get).toList();
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
        List<Long> countList = new ArrayList<>(Collections.nCopies(SIZE, 0L));

        Stream.concat(ownersList.stream(), testersList.stream())
                .forEach(ingred -> {
                    int idx = ingred.getPriority()-1;
                    long value = countList.get(idx);

                    // 해당 값 증가시키기
                    countList.set(idx, value + 1);
                });

        int maxIndex = IntStream.range(0, countList.size())
                .boxed()
                .max(Comparator.comparing(countList::get))
                .orElse(0);

        return IngredSubType.values()[maxIndex].getCoreName();
    }
}
