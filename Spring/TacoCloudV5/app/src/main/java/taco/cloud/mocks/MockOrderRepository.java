package taco.cloud.mocks;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import taco.cloud.models.Ingredient;
import taco.cloud.models.IngredientUDT;
import taco.cloud.models.TacoOrder;
import taco.cloud.models.TacoUDT;
import taco.cloud.repositories.OrderRepository;

@Repository
@Primary
public class MockOrderRepository implements OrderRepository {

    private final List<TacoOrder> orders;

    public MockOrderRepository() {
        IngredientUDT ingredient1 = new IngredientUDT("Bread", Ingredient.Type.WRAP);
        IngredientUDT ingredient2 = new IngredientUDT("Beef", Ingredient.Type.PROTEIN);
        IngredientUDT ingredient3 = new IngredientUDT("Tommato", Ingredient.Type.VEGGIES);
        IngredientUDT ingredient4 = new IngredientUDT("Cheese", Ingredient.Type.CHEESE);
        IngredientUDT ingredient5 = new IngredientUDT("Oil", Ingredient.Type.SAUCE);
        TacoUDT taco = new TacoUDT("My taco", List.of(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5));
        TacoOrder mockOrder = new TacoOrder(UUID.randomUUID(), "My order", "Street", "City", "State", "Zip",
                "79927398713", "03/25", "123", Date.from(Instant.now()), List.of(taco));

        this.orders = List.of(mockOrder);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(TacoOrder entity) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void deleteAll(Iterable<? extends TacoOrder> entities) {
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
    public Iterable<TacoOrder> findAll() {
        return orders;
    }

    @Override
    public Iterable<TacoOrder> findAllById(Iterable<String> ids) {
        return null;
    }

    @Override
    public Optional<TacoOrder> findById(String id) {
        return Optional.empty();
    }

    @Override
    public <S extends TacoOrder> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TacoOrder> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

}
