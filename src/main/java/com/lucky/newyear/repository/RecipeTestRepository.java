package com.lucky.newyear.repository;


import com.lucky.newyear.entity.RecipeTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeTestRepository extends JpaRepository<RecipeTest, Long> {

    Optional<RecipeTest> findByOwnerUUID(String ownerUUID);


}