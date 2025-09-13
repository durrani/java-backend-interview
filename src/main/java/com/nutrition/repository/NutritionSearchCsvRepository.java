package com.nutrition.repository;

import java.nio.charset.StandardCharsets;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.nutrition.model.Food;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

@Repository
@ConditionalOnProperty(name = NutritionSearchRepository.PROPERTY_NAME, havingValue = NutritionSearchRepository.EXTENSION_CSV)
public final class NutritionSearchCsvRepository implements NutritionSearchRepository {
    
    private String nutritionDataFile;

    public NutritionSearchCsvRepository(@Value("${nutrition-search.data.file.name.csv}") String nutritionDataFile) {
        this.nutritionDataFile = nutritionDataFile;
    }

    @Override
    public List<Food> loadNutritionData() {
        try (var csvReader = new CSVReaderHeaderAware(new FileReader(loadNutritionFile(nutritionDataFile), StandardCharsets.UTF_8))) {

            Map<String, String> rowData;
            var foods = new ArrayList<Food>();

            while ((rowData = csvReader.readMap()) != null) {
                if (isValidFood(rowData)) {
                    foods.add(createFood(rowData));
                }
            }

            return foods;
        } catch (IOException | CsvValidationException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
