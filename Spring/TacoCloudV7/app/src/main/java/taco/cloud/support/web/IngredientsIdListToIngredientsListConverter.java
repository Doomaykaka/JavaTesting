package taco.cloud.support.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.cloud.models.Ingredient;
import taco.cloud.repositories.IngredientRepository;

@Component
public class IngredientsIdListToIngredientsListConverter implements Converter<List<Long>, List<Ingredient>> {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientsIdListToIngredientsListConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> convert(List<Long> idList) {
        List<Ingredient> result = new ArrayList<>();

        for (Long id : idList) {
            Ingredient object = ingredientRepository.findById(id).orElse(null);
            if (object != null) {
                result.add(object);
            }
        }

        return result;
    }

}
