package com.lucky.newyear.model.enums;

import lombok.Getter;

@Getter
public enum IngredYuksuType {
    BONE("사골", 1, 1, 2),
    ANCHOVY("멸치", 1, 2, 4),
    CHICKEN("닭", 1, 2, 6),
    SEAFOOD("해물", 1, 2, 5),
    MARA("마라", 2, 1, 1),
    TOMATO("토마토", 2, 2, 3);

    private final String coreName;
    private final Integer combi1;   // 사골 + (멸치, 닭, 해물) 조합 점수
    private final Integer combi2;   // 멸치, 닭, 해물을 (사골)에 조합해야함. combi1과 연계
    private final Integer priority; // 네이밍 우선순위

    IngredYuksuType(String coreName, Integer combi1, Integer combi2, Integer priority) {
        this.coreName = coreName;
        this.combi1 = combi1;
        this.combi2 = combi2;
        this.priority = priority;
    }

    public static IngredYuksuType fromIndex(int index) {
        return IngredYuksuType.values()[index];
    }

    public static IngredYuksuType getIngredientByDescript(String descript) {
        for (IngredYuksuType ingredient : values()) {
            if (ingredient.coreName.equals(descript)) {
                return ingredient;
            }
        }
        return null;
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
