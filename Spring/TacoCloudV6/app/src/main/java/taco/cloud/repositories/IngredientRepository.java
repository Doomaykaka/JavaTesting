package taco.cloud.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import taco.cloud.models.Ingredient;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
}
