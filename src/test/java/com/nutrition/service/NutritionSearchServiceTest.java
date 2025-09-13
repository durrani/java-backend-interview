package com.nutrition.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.nutrition.dto.NutritionSearchRequestFactory;
import com.nutrition.model.Food;
import com.nutrition.repository.NutritionSearchRepository;

public class NutritionSearchServiceTest {
    
    @Mock
    private NutritionSearchRepository repository;

    @InjectMocks
    private NutritionSearchService service;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(repository.loadNutritionData()).thenReturn(List.of(
            new Food("Apple", 95, 0.3, Food.FatRating.LOW, "0mg"),
            new Food("Banana", 105, 0.4, Food.FatRating.MEDIUM, "0mg"),
            new Food("Cheeseburger", 303, 1.2, Food.FatRating.HIGH, "0mg")
        ));
    }

    @Test
    public void testSearchNutritionDataForDefaultLimit() {
        var request = NutritionSearchRequestFactory.newRequest(94, 900, List.of(), null, 1000);
        var results = service.searchNutrition(request);
        assert results.size() == 3 : "Expected 3 items, but got " + results.size();
    }

    @Test
    public void testSearchNutritionDataForMinCaloriesEdgeCase() {
        var request = NutritionSearchRequestFactory.newRequest(95, 900, List.of(), null, 1000);
        var results = service.searchNutrition(request);
        assert results.size() == 3 : "Expected 3 items, but got " + results.size();
    }

    @Test
    public void testSearchNutritionDataForMinCalories() {
        var request = NutritionSearchRequestFactory.newRequest(96, 900, List.of(), null, 1000);
        var results = service.searchNutrition(request); 
        assert results.size() == 2 : "Expected 2 items, but got " + results.size();
    }
}
