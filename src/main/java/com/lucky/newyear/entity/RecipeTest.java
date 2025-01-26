package com.lucky.newyear.entity;

import com.lucky.newyear.model.Recipe;
import com.lucky.newyear.utill.RecipeConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "recipe_test")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecipeTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;    // 결국은 id를 이용한 검색이 될수도.

    @Column(name = "owner_uuid")
    private String ownerUUID;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "recipe")
    @Convert(converter = RecipeConverter.class)
    private Recipe recipe;

    @Column(name = "tester_count")
    private Integer testerCount;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Version
    private Long version;

    public void increaseTesterCount() {
        this.testerCount += 1;
    }
}
