package com.lucky.newyear.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Recipe {
    private List<Long> broth;
    private List<Long> main;
    private List<Long> sub;
    private List<Long> garnish;
}
