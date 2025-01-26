package com.lucky.newyear.entity;

import com.lucky.newyear.entity.compositeKey.RecipeTestRecordId;
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
    private Long score;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "message")
    private String message;

}
