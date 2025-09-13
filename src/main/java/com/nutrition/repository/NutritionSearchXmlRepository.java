package com.nutrition.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.nutrition.model.Food;

@Repository
@ConditionalOnProperty(name = NutritionSearchRepository.PROPERTY_NAME, havingValue = NutritionSearchRepository.EXTENSION_XML)
public final class NutritionSearchXmlRepository implements NutritionSearchRepository {

    private String nutritionDataFile;

    public NutritionSearchXmlRepository(@Value("${nutrition-search.data.file.name.xml}") String nutritionDataFile) {
        this.nutritionDataFile = nutritionDataFile;
    }

    @Override
    public List<Food> loadNutritionData() {
        throw new UnsupportedOperationException("Unimplemented method 'loadNutritionData' " + nutritionDataFile);
    }

}
