package taco.cloud.mocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import taco.cloud.models.Ingredient;
import taco.cloud.models.Ingredient.Type;
import taco.cloud.repositories.IngredientRepository;

@Repository
@Primary
public class MockIngredientRepository implements IngredientRepository {

    private final List<Ingredient> ingredients;

    public MockIngredientRepository() {
        this.ingredients = Arrays.asList(new Ingredient(0, "Flour Tortilla", Type.WRAP),
                new Ingredient(1, "Corn Tortilla", Type.WRAP), new Ingredient(2, "Ground Beef", Type.PROTEIN),
                new Ingredient(3, "Grilled Chicken", Type.PROTEIN), new Ingredient(4, "Lettuce", Type.VEGGIES),
                new Ingredient(5, "Tomato", Type.VEGGIES), new Ingredient(6, "Shredded Cheese", Type.CHEESE),
                new Ingredient(7, "Sour Cream", Type.SAUCE), new Ingredient(8, "Salsa", Type.SAUCE));
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return ingredients;
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        return ingredients.stream().filter(i -> i.getId() == Integer.parseInt(id)).findFirst();
    }

    @Override
    public long count() {
        return ingredients.size();
    }

    @Override
    public void delete(Ingredient entity) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void deleteAll(Iterable<? extends Ingredient> entities) {
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public Iterable<Ingredient> findAllById(Iterable<String> ids) {
        return null;
    }

    @Override
    public <S extends Ingredient> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends Ingredient> Iterable<S> saveAll(Iterable<S> entities) {
        return entities;
    }
}
