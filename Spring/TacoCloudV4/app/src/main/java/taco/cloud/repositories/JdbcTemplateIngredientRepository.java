package taco.cloud.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.Ingredient;

public interface JdbcTemplateIngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findByName(String name);
}
