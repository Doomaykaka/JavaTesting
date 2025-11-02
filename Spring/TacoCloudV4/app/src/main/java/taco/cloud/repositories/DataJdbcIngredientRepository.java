package taco.cloud.repositories;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import taco.cloud.models.Ingredient;

public interface DataJdbcIngredientRepository extends Repository<Ingredient, Long> {
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(Long id);

    Ingredient save(Ingredient ingredient);
}
