package taco.cloud.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.cloud.models.Ingredient;
import taco.cloud.models.IngredientUDT;
import taco.cloud.repositories.IngredientRepository;

@Component
public class IngredientUDTByStringIdConverter implements Converter<String, IngredientUDT> {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientUDTByStringIdConverter(IngredientRepository ingredientRepository) {
        super();
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientUDT convert(String id) {
        Ingredient result = ingredientRepository.findById(id).orElse(null);

        if (result == null) {
            return null;
        }

        return new IngredientUDT(result.getName(), result.getType());
    }
}
