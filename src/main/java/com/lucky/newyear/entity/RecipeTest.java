package com.lucky.newyear.entity;

import com.lucky.newyear.model.Recipe;
import com.lucky.newyear.utill.RecipeConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "recipe_test_record")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecipeTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "room_token")
    private String roomToken;

    @Column(name = "master_uuid")
    private String masterUuid;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "recipe")
    @Convert(converter = RecipeConverter.class)
    private Recipe recipe;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

}
