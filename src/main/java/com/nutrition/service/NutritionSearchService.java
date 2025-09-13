package com.nutrition.service;

import com.nutrition.dto.NutritionSearchRequest;
import com.nutrition.dto.Sort;
import com.nutrition.dto.SortField;
import com.nutrition.dto.SortOrder;
import com.nutrition.model.Food;
import com.nutrition.repository.NutritionSearchRepository;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public final class NutritionSearchService {

    private static final EnumMap<SortField, Comparator<Food>> FIELD_COMPARATORS = new EnumMap<>(SortField.class);

    static {
        FIELD_COMPARATORS.put(SortField.CALORIES, Comparator.comparing(
            Food::calories, Comparator.naturalOrder()));
        FIELD_COMPARATORS.put(SortField.NAME, Comparator.comparing(
            Food::name, String.CASE_INSENSITIVE_ORDER));
    }

    private final NutritionSearchRepository repository;

    public NutritionSearchService(NutritionSearchRepository repository) {
        this.repository = repository;
    }

    public List<Food> searchNutrition(NutritionSearchRequest request) {
        // handle UnsupportedOperationException from repository.loadNutritionData()
        try {
            return repository.loadNutritionData().stream()
                .filter(item -> search(request, item))
                .limit(request.limit())
                .sorted(buildComparator(request))
                .toList();
        } catch (UnsupportedOperationException e) {
            // log the exception and return an empty list
            e.printStackTrace();
            return List.of();
        }
    }

    private boolean search(NutritionSearchRequest request, Food item) {
        return item.calories() >= request.minCalories() 
            && item.calories() <= request.maxCalories();
            
    }

    private Comparator<Food> buildComparator(NutritionSearchRequest request) {
        return request.sorts().stream()
            .map(this::getComparator)
            .reduce(Comparator::thenComparing)
            .orElse((h1, h2) -> 0);
    }

    private Comparator<Food> getComparator(Sort sort) {
        var comparator = FIELD_COMPARATORS.get(sort.field());
        return sort.order() == SortOrder.ASC ? comparator : comparator.reversed();
    }

}
