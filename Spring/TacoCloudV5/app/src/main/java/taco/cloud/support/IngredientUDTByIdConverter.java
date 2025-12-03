package taco.cloud.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.cloud.models.Ingredient;
import taco.cloud.models.IngredientUDT;
import taco.cloud.repositories.IngredientRepository;

@Component
public class IngredientUDTByIdConverter implements Converter<String, IngredientUDT> {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientUDTByIdConverter(IngredientRepository ingredientRepository) {
        super();
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientUDT convert(String id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }
}
