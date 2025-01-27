package com.lucky.newyear.repository;

import com.lucky.newyear.entity.RecipeTestRecord;
import com.lucky.newyear.entity.compositeKey.RecipeTestRecordId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeTestRecordRepository extends JpaRepository<RecipeTestRecord, RecipeTestRecordId> {
    List<RecipeTestRecord> findTop4ByIdTestIdOrderByScoreDesc(Long testId);

    Page<RecipeTestRecord> findAllByIdTestId(Pageable pageable, Long testId);
}
