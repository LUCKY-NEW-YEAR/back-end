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
    private List<Long> yuksu;   // 육수
    private List<Long> main;    // 주재료
    private List<Long> sub;     // 부재료
    private List<Long> garnish; //
}
