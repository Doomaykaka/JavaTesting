package taco.cloud.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.Ingredient;

@Profile("prod")
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
