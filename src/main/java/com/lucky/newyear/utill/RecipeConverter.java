package com.lucky.newyear.utill;

import com.lucky.newyear.model.Recipe;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class RecipeConverter implements AttributeConverter<Recipe, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Recipe recipe) {
        try {
            if (recipe == null) {
                throw new RuntimeException("recipe is null");
            }
            return objectMapper.writeValueAsString(recipe);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON 변환 오류: " + e.getMessage(), e);
        }
    }

    @Override
    public Recipe convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null) {
                return null;
            }
            return objectMapper.readValue(dbData, Recipe.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON 읽기 오류: " + e.getMessage(), e);
        }
    }
}
