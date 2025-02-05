package com.lucky.newyear.entity.compositeKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class RecipeTestRecordId implements Serializable {
    @Column(name = "test_id")
    private Long testId;

    @Column(name = "user_uuid")
    private String userUUID;
}
