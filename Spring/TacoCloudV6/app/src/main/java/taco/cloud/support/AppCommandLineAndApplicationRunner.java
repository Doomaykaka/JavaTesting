package taco.cloud.support;

import java.util.Arrays;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import taco.cloud.models.Ingredient;
import taco.cloud.models.Ingredient.Type;
import taco.cloud.repositories.IngredientRepository;

@Configuration
public class AppCommandLineAndApplicationRunner {
    @Bean
    public CommandLineRunner commandLineRunnerTest() {
        return args -> {
            System.out.println("Command line runner works!");
        };
    }

    @Bean
    public ApplicationRunner applicationRunnerTest() {
        return args -> {
            System.out.println("Application runner works!");
        };
    }

    @Bean
    public CommandLineRunner fillDatabase(IngredientRepository ingredientRepository) {
        return args -> {
            java.util.List<Ingredient> ingredients = Arrays.asList(new Ingredient("0", "Flour Tortilla", Type.WRAP),
                    new Ingredient("1", "Corn Tortilla", Type.WRAP), new Ingredient("2", "Ground Beef", Type.PROTEIN),
                    new Ingredient("3", "Grilled Chicken", Type.PROTEIN), new Ingredient("4", "Lettuce", Type.VEGGIES),
                    new Ingredient("5", "Tomato", Type.VEGGIES), new Ingredient("6", "Shredded Cheese", Type.CHEESE),
                    new Ingredient("7", "Sour Cream", Type.SAUCE), new Ingredient("8", "Salsa", Type.SAUCE));

            ingredientRepository.saveAll(ingredients);
        };
    }
}
