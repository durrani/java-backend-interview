package com.nutrition.repository;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.nutrition.model.Food;

public class NutritionSearchCsvRepositoryTest {
    
    @Test
    void testLoadNutritionData() {
        NutritionSearchCsvRepository repository = new NutritionSearchCsvRepository("nutrition.csv");
        List<Food> foods = repository.loadNutritionData();
        assert !foods.isEmpty();
        assert foods.size() == 8789 : "Expected 8789 items, but got " + foods.size();
    }
}
