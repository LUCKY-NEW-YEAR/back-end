package com.lucky.newyear.model;

import com.lucky.newyear.model.enums.IngredGarnishType;
import com.lucky.newyear.model.enums.IngredMainType;
import com.lucky.newyear.model.enums.IngredSubType;
import com.lucky.newyear.model.enums.IngredYuksuType;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recipe {
    private List<Integer> yuksu;   // 육수
    private List<Integer> main;    // 주재료
    private List<Integer> sub;     // 부재료
    private List<Integer> garnish; //

    public static Recipe of(
             final List<String> yuksuList,
             final List<String> mainList,
             final List<String> subList,
             final List<String> garnishList
    ) {
        List<Integer> intYuksu = yuksuList.stream().map(
                yuksu -> IngredYuksuType.valueOf(yuksu.toUpperCase()).getId()
        ).toList();

        List<Integer> intMain = mainList.stream().map(
                main -> IngredMainType.valueOf(main.toUpperCase()).getId()
        ).toList();

        List<Integer> intSub = subList.stream().map(
                sub -> IngredSubType.valueOf(sub.toUpperCase()).getId()
        ).toList();

        List<Integer> intGarnish = garnishList.stream().map(
                garnish -> IngredGarnishType.valueOf(garnish.toUpperCase()).getId()
        ).toList();

        return Recipe.builder()
                .yuksu(intYuksu)
                .main(intMain)
                .sub(intSub)
                .garnish(intGarnish)
                .build();
    }

    public static Recipe merge(Recipe r1, Recipe r2) {
        Recipe mergedRecipe = new Recipe();
        // 중복 제거 후 병합
        mergedRecipe.yuksu = mergeLists(r1.yuksu, r2.yuksu);
        mergedRecipe.main = mergeLists(r1.main, r2.main);
        mergedRecipe.sub = mergeLists(r1.sub, r2.sub);
        mergedRecipe.garnish = mergeLists(r1.garnish, r2.garnish);

        return mergedRecipe;
    }
    private static List<Integer> mergeLists(List<Integer> list1, List<Integer> list2) {
        Set<Integer> mergedSet = new HashSet<>(list1);
        mergedSet.addAll(list2);
        return new ArrayList<>(mergedSet);
    }
}
