package taco.cloud.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findByName(String name);
}
