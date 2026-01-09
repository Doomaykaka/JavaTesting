package taco.cloud.support.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import taco.cloud.models.Ingredient;
import taco.cloud.models.Ingredient.Type;
import taco.cloud.repositories.IngredientRepository;

@Configuration
public class AppCommandLineAndApplicationRunner {
    private final long INGREDIENT_1_ID = 0;
    private final String INGREDIENT_1_NAME = "Flour Tortilla";
    private final Type INGREDIENT_1_TYPE = Type.WRAP;
    private final long INGREDIENT_2_ID = 1;
    private final String INGREDIENT_2_NAME = "Corn Tortilla";
    private final Type INGREDIENT_2_TYPE = Type.WRAP;
    private final long INGREDIENT_3_ID = 2;
    private final String INGREDIENT_3_NAME = "Ground Beef";
    private final Type INGREDIENT_3_TYPE = Type.PROTEIN;
    private final long INGREDIENT_4_ID = 3;
    private final String INGREDIENT_4_NAME = "Carnitas";
    private final Type INGREDIENT_4_TYPE = Type.PROTEIN;
    private final long INGREDIENT_5_ID = 4;
    private final String INGREDIENT_5_NAME = "Diced Tomatoes";
    private final Type INGREDIENT_5_TYPE = Type.VEGGIES;
    private final long INGREDIENT_6_ID = 5;
    private final String INGREDIENT_6_NAME = "Lettuce";
    private final Type INGREDIENT_6_TYPE = Type.VEGGIES;
    private final long INGREDIENT_7_ID = 6;
    private final String INGREDIENT_7_NAME = "Cheddar";
    private final Type INGREDIENT_7_TYPE = Type.CHEESE;
    private final long INGREDIENT_8_ID = 7;
    private final String INGREDIENT_8_NAME = "Monterrey Jack";
    private final Type INGREDIENT_8_TYPE = Type.CHEESE;
    private final long INGREDIENT_9_ID = 8;
    private final String INGREDIENT_9_NAME = "Salsa";
    private final Type INGREDIENT_9_TYPE = Type.SAUCE;
    private final long INGREDIENT_10_ID = 9;
    private final String INGREDIENT_10_NAME = "Sour Cream";
    private final Type INGREDIENT_10_TYPE = Type.SAUCE;

    @Bean
    public CommandLineRunner commandLineRunnerTest() {
        return args -> {
            System.out.println("Command line runner works!");
        };
    }

    @Bean
    public ApplicationRunner applicationRunnerTest(IngredientRepository repository) {
        return args -> {
            System.out.println("Application runner works!");

            fillIngredientsTable(repository);
        };
    }

    private void fillIngredientsTable(IngredientRepository repository) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient(INGREDIENT_1_ID, INGREDIENT_1_NAME, INGREDIENT_1_TYPE),
                new Ingredient(INGREDIENT_2_ID, INGREDIENT_2_NAME, INGREDIENT_2_TYPE),
                new Ingredient(INGREDIENT_3_ID, INGREDIENT_3_NAME, INGREDIENT_3_TYPE),
                new Ingredient(INGREDIENT_4_ID, INGREDIENT_4_NAME, INGREDIENT_4_TYPE),
                new Ingredient(INGREDIENT_5_ID, INGREDIENT_5_NAME, INGREDIENT_5_TYPE),
                new Ingredient(INGREDIENT_6_ID, INGREDIENT_6_NAME, INGREDIENT_6_TYPE),
                new Ingredient(INGREDIENT_7_ID, INGREDIENT_7_NAME, INGREDIENT_7_TYPE),
                new Ingredient(INGREDIENT_8_ID, INGREDIENT_8_NAME, INGREDIENT_8_TYPE),
                new Ingredient(INGREDIENT_9_ID, INGREDIENT_9_NAME, INGREDIENT_9_TYPE),
                new Ingredient(INGREDIENT_10_ID, INGREDIENT_10_NAME, INGREDIENT_10_TYPE));
        
        repository.saveAll(ingredients);
    }
}
