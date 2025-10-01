package taco.cloud.support;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco.cloud.models.Ingredient;
import taco.cloud.models.Ingredient.Type;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final String INGREDIENT_1_ID = "FLTO";
    private final String INGREDIENT_1_NAME = "Flour Tortilla";
    private final Type INGREDIENT_1_TYPE = Type.WRAP;
    private final String INGREDIENT_2_ID = "COTO";
    private final String INGREDIENT_2_NAME = "Corn Tortilla";
    private final Type INGREDIENT_2_TYPE = Type.WRAP;
    private final String INGREDIENT_3_ID = "GRBF";
    private final String INGREDIENT_3_NAME = "Ground Beef";
    private final Type INGREDIENT_3_TYPE = Type.PROTEIN;
    private final String INGREDIENT_4_ID = "CARN";
    private final String INGREDIENT_4_NAME = "Carnitas";
    private final Type INGREDIENT_4_TYPE = Type.PROTEIN;
    private final String INGREDIENT_5_ID = "TMTO";
    private final String INGREDIENT_5_NAME = "Diced Tomatoes";
    private final Type INGREDIENT_5_TYPE = Type.VEGGIES;
    private final String INGREDIENT_6_ID = "LETC";
    private final String INGREDIENT_6_NAME = "Lettuce";
    private final Type INGREDIENT_6_TYPE = Type.VEGGIES;
    private final String INGREDIENT_7_ID = "CHED";
    private final String INGREDIENT_7_NAME = "Cheddar";
    private final Type INGREDIENT_7_TYPE = Type.CHEESE;
    private final String INGREDIENT_8_ID = "JACK";
    private final String INGREDIENT_8_NAME = "Monterrey Jack";
    private final Type INGREDIENT_8_TYPE = Type.CHEESE;
    private final String INGREDIENT_9_ID = "SLSA";
    private final String INGREDIENT_9_NAME = "Salsa";
    private final Type INGREDIENT_9_TYPE = Type.SAUCE;
    private final String INGREDIENT_10_ID = "SRCR";
    private final String INGREDIENT_10_NAME = "Sour Cream";
    private final Type INGREDIENT_10_TYPE = Type.SAUCE;

    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put(INGREDIENT_1_ID, new Ingredient(INGREDIENT_1_ID, INGREDIENT_1_NAME, INGREDIENT_1_TYPE));
        ingredientMap.put(INGREDIENT_2_ID, new Ingredient(INGREDIENT_2_ID, INGREDIENT_2_NAME, INGREDIENT_2_TYPE));
        ingredientMap.put(INGREDIENT_3_ID, new Ingredient(INGREDIENT_3_ID, INGREDIENT_3_NAME, INGREDIENT_3_TYPE));
        ingredientMap.put(INGREDIENT_4_ID, new Ingredient(INGREDIENT_4_ID, INGREDIENT_4_NAME, INGREDIENT_4_TYPE));
        ingredientMap.put(INGREDIENT_5_ID, new Ingredient(INGREDIENT_5_ID, INGREDIENT_5_NAME, INGREDIENT_5_TYPE));
        ingredientMap.put(INGREDIENT_6_ID, new Ingredient(INGREDIENT_6_ID, INGREDIENT_6_NAME, INGREDIENT_6_TYPE));
        ingredientMap.put(INGREDIENT_7_ID, new Ingredient(INGREDIENT_7_ID, INGREDIENT_7_NAME, INGREDIENT_7_TYPE));
        ingredientMap.put(INGREDIENT_8_ID, new Ingredient(INGREDIENT_8_ID, INGREDIENT_8_NAME, INGREDIENT_8_TYPE));
        ingredientMap.put(INGREDIENT_9_ID, new Ingredient(INGREDIENT_9_ID, INGREDIENT_9_NAME, INGREDIENT_9_TYPE));
        ingredientMap.put(INGREDIENT_10_ID, new Ingredient(INGREDIENT_10_ID, INGREDIENT_10_NAME, INGREDIENT_10_TYPE));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
