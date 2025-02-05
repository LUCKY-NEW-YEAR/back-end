package com.lucky.newyear.entity;

import com.lucky.newyear.entity.compositeKey.RecipeTestRecordId;
import com.lucky.newyear.model.Recipe;
import com.lucky.newyear.utill.RecipeConverter;
import jakarta.persistence.*;

import lombok.*;

import java.util.Date;

@Entity
@Table(name = "recipe_test_record")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecipeTestRecord {

    @EmbeddedId
    private RecipeTestRecordId id;

    @MapsId("testId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private RecipeTest recipeTest;

    @Column(name = "score")
    private Integer score;

    @Column(name = "title")
    private String title;

    @Column(name = "recipe")
    @Convert(converter = RecipeConverter.class)
    private Recipe recipe;

    @Column(name = "nickname")
    private String nicknameEnc;    // 암호화 필요.?

    @Column(name = "message")
    private String message;

}
