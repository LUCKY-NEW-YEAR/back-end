package com.lucky.newyear.model.enums;

import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

@Getter
public enum IngredMainType {
    GARAE(1, "가래떡", 0, 0),
    TONGGARAE(2, "통가래떡", 0, 0),
    JORAENG(3, "조랭이떡", 0, 0),
    GGULDDUK(4, "꿀떡", 0, 1),
    KIMCHI(5, "김치만두", 1, 0),
    GOGI(6, "고기만두", 1, 0),
    GULRIM(7, "굴림만두", 1, 2);

    private static final Map<Integer, IngredMainType> intToStringMap = new HashMap<>();

    static {
        for (IngredMainType type : values()) {
            intToStringMap.put(type.getId(), type);
        }
    }

    private final Integer id;
    private final String descript;
    private final Integer combi1;   // 떡 3개 || 만두 3개 들어갈 경우 감점.
    private final Integer combi2;   // 꿀떡 & 굴림만두 조합

    IngredMainType(Integer id, String descript, Integer combi1, Integer combi2) {
        this.id = id;
        this.descript = descript;
        this.combi1 = combi1;
        this.combi2 = combi2;
    }

    public static IngredMainType fromId(int id) {
        return intToStringMap.get(id);
    }

    public static List<IngredMainType> fromIds(List<Integer> idList) {
        return idList.stream().map(intToStringMap::get).toList();
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
