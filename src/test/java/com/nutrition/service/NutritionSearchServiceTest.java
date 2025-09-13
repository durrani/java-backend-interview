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

    private Food apple = new Food("Apple", 95, 0.3, Food.FatRating.LOW, "0mg");
    private Food banana =         new Food("Banana", 105, 0.4, Food.FatRating.MEDIUM, "0mg");
    private Food cheesburger =         new Food("Cheeseburger", 303, 1.2, Food.FatRating.HIGH, "0mg");
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(repository.loadNutritionData()).thenReturn(List.of(
            apple,
            banana,
            cheesburger
        ));
    }

    @Test
    public void testSearchNutritionDataForDefaultLimit() {
        var request = NutritionSearchRequestFactory.newRequest(94, 900, List.of(), null, 1000);
        var results = service.searchNutrition(request);
        assert results.size() == 3 : "Expected 3 items, but got " + results.size();
        assert results.get(0).equals(apple) : "Expected first item to be apple, but got " + results.get(0).name();
        assert results.get(1).equals(banana) : "Expected second item to be banana, but got " + results.get(1).name();
        assert results.get(2).equals(cheesburger) : "Expected third item to be cheesburger, but got " + results.get(2).name();
    }

    @Test
    public void testSearchNutritionDataForMinCaloriesEdgeCase() {
        var request = NutritionSearchRequestFactory.newRequest(105, 900, List.of(), null, 1000);
        var results = service.searchNutrition(request);
        assert results.size() == 2 : "Expected 2 items, but got " + results.size();
        assert results.get(0).equals(banana) : "Expected first item to be banana, but got " + results.get(0).name();
        assert results.get(1).equals(cheesburger) : "Expected second item to be cheesburger, but got " + results.get(1).name();
    }

    @Test
    public void testSearchNutritionDataForMinCalories() {
        var request = NutritionSearchRequestFactory.newRequest(96, 900, List.of(), null, 1000);
        var results = service.searchNutrition(request); 
        assert results.size() == 2 : "Expected 2 items, but got " + results.size();
        assert results.get(0).equals(banana) : "Expected first item to be banana, but got " + results.get(0).name();
        assert results.get(1).equals(cheesburger) : "Expected second item to be cheesburger, but got " + results.get(1).name();   
    }

    @Test
    public void testSearchNutritionDataForMaxCaloriesEdgeCase() {
        var request = NutritionSearchRequestFactory.newRequest(90, 105, List.of(), null, 1000);
        var results = service.searchNutrition(request); 
        assert results.size() == 2 : "Expected 2 items, but got " + results.size();
        assert results.get(0).equals(apple) : "Expected first item to be apple, but got " + results.get(0).name();
        assert results.get(1).equals(banana) : "Expected second item to be banana, but got " + results.get(1).name();
    }
    
    @Test
    public void testSearchNutritionDataForMaxCalories() {
        var request = NutritionSearchRequestFactory.newRequest(90, 106, List.of(), null, 1000);
        var results = service.searchNutrition(request); 
        assert results.size() == 2 : "Expected 2 items, but got " + results.size();
        assert results.get(0).equals(apple) : "Expected first item to be apple, but got " + results.get(0).name();
        assert results.get(1).equals(banana) : "Expected second item to be banana, but got " + results.get(1).name();
    }

    @Test
    public void testSearchNutritionDataForExactCalories() {
        var request = NutritionSearchRequestFactory.newRequest(105, 105, List.of(), null, 1000);
        var results = service.searchNutrition(request); 
        assert results.size() == 1 : "Expected 1 item, but got " + results.size();
        assert results.get(0).equals(banana) : "Expected first item to be banana, but got " + results.get(0).name();
    }

}
