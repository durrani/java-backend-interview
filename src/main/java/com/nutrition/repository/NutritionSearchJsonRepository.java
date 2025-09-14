package com.nutrition.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.nutrition.model.Food;

@Repository
@ConditionalOnProperty(name = NutritionSearchRepository.EXTENSION_SOURCE, havingValue = NutritionSearchRepository.EXTENSION_JSON)
public final class NutritionSearchJsonRepository implements NutritionSearchRepository {

    private String nutritionDataFile;

    public NutritionSearchJsonRepository(@Value("${nutrition-search.data.file.name.json}") String nutritionDataFile) {
        this.nutritionDataFile = nutritionDataFile;
    }

    @Override
    public List<Food> loadNutritionData() {
        throw new UnsupportedOperationException("Unimplemented method 'loadNutritionData' " + nutritionDataFile);
    }

}
