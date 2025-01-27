package com.lucky.newyear.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum IngredYuksuType {
    SAGOL(1, "사골", 1, 1, 2),
    MYULCHI(2, "멸치", 1, 2, 4),
    DAK(3, "닭", 1, 2, 6),
    HAEMUL(4, "해물", 1, 2, 5),
    MARA(5, "마라", 2, 1, 1),
    TOMATO(6, "토마토", 2, 2, 3);

    private static final Map<Integer, IngredYuksuType> intToStringMap = new HashMap<>();

    static {
        // String -> Long 변환 테이블 생성
        for (IngredYuksuType type : values()) {
            intToStringMap.put(type.getId(), type);
        }
    }

    private final Integer id;
    private final String coreName;
    private final Integer combi1;   // 사골 + (멸치, 닭, 해물) 조합 점수
    private final Integer combi2;   // 멸치, 닭, 해물을 (사골)에 조합해야함. combi1과 연계
    private final Integer priority; // 네이밍 우선순위

    IngredYuksuType(Integer id, String coreName, Integer combi1, Integer combi2, Integer priority) {
        this.id = id;
        this.coreName = coreName;
        this.combi1 = combi1;
        this.combi2 = combi2;
        this.priority = priority;
    }

    public static IngredYuksuType fromId(int id) {
        return intToStringMap.get(id);
    }

    public static IngredYuksuType firstFromIds(List<Integer> idList) {
        Integer id = idList.get(0);
        return intToStringMap.get(id);
    }

    public static Integer calculYuksuScore(IngredYuksuType owners, IngredYuksuType testers) {
        // 동일 육수 -> 70
        if (owners.equals(testers)) {
            return 70;
        }
        if (owners.getCombi1().equals(testers.getCombi1())
            &&!owners.getCombi2().equals(testers.getCombi2())) {
            switch (owners.getCombi1()) {
                case 1:
                    // 사골 + (멸치, 닭, 해물) -> 60
                    return 60;
                case 2:
                    // 마라 + 토마토 -> 80
                    return 80;
            }
        }
        return 50;
    }

    public static String getYuksuName(IngredYuksuType owners, IngredYuksuType testers) {
        if (owners.getPriority() <= testers.getPriority()) {
            return owners.getCoreName();
        } else {
            return testers.getCoreName();
        }
    }
}
