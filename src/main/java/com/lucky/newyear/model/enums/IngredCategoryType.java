package com.lucky.newyear.model.enums;

import java.util.HashMap;
import java.util.Map;

// 재료 카테고리
public enum IngredCategoryType {
    BROTH("육수", 8),
    MAIN("주재료", 8),
    SUB("부재료", 8),
    GARNISH("고명", 8);

    private final String name;
    private final int typeCount;


    IngredCategoryType(String name, int typeCount) {
        this.name = name;
        this.typeCount = typeCount;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public static IngredCategoryType getIngredientByName(String name) {
        for (IngredCategoryType ingredient : values()) {
            if (ingredient.name.equals(name)) {
                return ingredient;
            }
        }
        return null;
    }
}
