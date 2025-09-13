package com.nutrition.repository;

import static com.nutrition.model.Food.FatRating.HIGH;
import static com.nutrition.model.Food.FatRating.LOW;
import static com.nutrition.model.Food.FatRating.MEDIUM;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import com.nutrition.exception.NutritionFileLoadingException;
import com.nutrition.model.Food;

import static com.nutrition.util.CsvColumnHeading.CAFFEINE_FIELD;
import static com.nutrition.util.CsvColumnHeading.CALORIES_FIELD;
import static com.nutrition.util.CsvColumnHeading.NAME_FIELD;
import static com.nutrition.util.CsvColumnHeading.SERVING_SIZE;
import static com.nutrition.util.CsvColumnHeading.TOTAL_FAT_FIELD;

public interface NutritionSearchRepository {
	public String PROPERTY_NAME = "nutrition-search.data.repository.source.extension";
	public String EXTENSION_CSV = "csv";
	public String EXTENSION_JSON = "json";
	public String EXTENSION_XML = "xml";

	List<Food> loadNutritionData();

    default File loadNutritionFile(String nutritionDataFile) {
        try {
            var nutritionData = new ClassPathResource(nutritionDataFile);
            return nutritionData.getFile();
        } catch (IOException ex) {
            throw new NutritionFileLoadingException(ex);
        }
    }
	
    default boolean isValidFood(Map<String, String> rowData) {
        return isValidRow(rowData) && rowData.get(SERVING_SIZE).equals("100 g");
    }

    private boolean isValidRow(Map<String, String> rowData) {
        var requiredFields = List.of(NAME_FIELD, CALORIES_FIELD, TOTAL_FAT_FIELD, CAFFEINE_FIELD, SERVING_SIZE);

        return rowData.entrySet().stream()
            .filter(e1 -> requiredFields.contains(e1.getKey()))
            .noneMatch(e2 -> e2.getValue().isBlank());
    }

    default Food createFood(Map<String, String> rowData) {
        var name = rowData.get(NAME_FIELD);
        var calories = Integer.parseInt(rowData.get(CALORIES_FIELD));
        var totalFat = Double.parseDouble(rowData.get(TOTAL_FAT_FIELD).substring(0, rowData.get(TOTAL_FAT_FIELD).indexOf('g')));
        var fatRating = getFatRating(totalFat);
        var caffeine = rowData.get(CAFFEINE_FIELD);

        return new Food(name, calories, totalFat, fatRating, caffeine);
    }

    private static Food.FatRating getFatRating(Double totalFat) {
        if (totalFat >= 17.5) {
            return HIGH;
        } else if (totalFat <= 3) {
            return LOW;
        } else {
            return MEDIUM;
        }
    }
}
