package taco.cloud.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.cloud.models.Ingredient;
import taco.cloud.models.IngredientUDT;
import taco.cloud.repositories.IngredientRepository;

@Component
public class IngredientsIdListToIngredientsUDTListConverter implements Converter<List<String>, List<IngredientUDT>> {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientsIdListToIngredientsUDTListConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientUDT> convert(List<String> idList) {
        List<IngredientUDT> result = new ArrayList<>();

        for (String id : idList) {
            Ingredient object = ingredientRepository.findById(id).orElse(null);
            if (object != null) {
                result.add(new IngredientUDT(object.getName(), object.getType()));
            }
        }

        return result;
    }
}
