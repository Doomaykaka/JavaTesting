package taco.cloud.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.cloud.models.Ingredient;
import taco.cloud.repositories.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<Long, Ingredient> {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        super();
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
