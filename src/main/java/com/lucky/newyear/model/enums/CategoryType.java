package com.lucky.newyear.model.enums;

public enum CategoryType {
    BROTH("육수", 8),
    MAIN("주재료", 8),
    SUB("부재료", 8),
    GARNISH("고명", 8);

    private final String name;
    private final int typeCount;

    CategoryType(String name, int typeCount) {
        this.name = name;
        this.typeCount = typeCount;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public static CategoryType getCategoryByName(String name) {
        for (CategoryType category : values()) {
            if (category.name.equals(name)) {
                return category;
            }
        }
        return null;
    }
}
