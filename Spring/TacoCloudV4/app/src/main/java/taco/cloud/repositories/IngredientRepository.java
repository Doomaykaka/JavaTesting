package taco.cloud.repositories;

import java.util.Optional;

import taco.cloud.models.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(Long id);

    Ingredient save(Ingredient ingredient);

    Ingredient update(Ingredient ingredient);

    boolean remove(Ingredient ingredient);
}
