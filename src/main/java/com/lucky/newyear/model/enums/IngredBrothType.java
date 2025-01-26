package com.lucky.newyear.model.enums;

public enum IngredBrothType {
    BONE("사골 육수", 1, 1),
    ANCHOVY("멸치 육수", 1, 2),
    CHICKEN("닭 육수", 1, 2),
    SEAFOOD("해물 육수", 1, 2),
    MARA("마라 육수", 2, 1),
    TOMATO("토마토 육수", 2, 2);

    private final String descript;
    private final Integer combi1;
    private final Integer combi2;

    IngredBrothType(String descript, Integer combi1, Integer combi2) {
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

    public static IngredBrothType getIngredientByDescript(String descript) {
        for (IngredBrothType ingredient : values()) {
            if (ingredient.descript.equals(descript)) {
                return ingredient;
            }
        }
        return null;
    }

    public static Integer calculBrothScore(IngredBrothType owners, IngredBrothType testers) {
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
}
