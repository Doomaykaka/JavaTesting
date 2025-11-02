package taco.cloud.repositories;

import java.util.Optional;

import taco.cloud.models.TacoOrder;

public interface OrderRepository {
    Iterable<TacoOrder> findAll();

    Optional<TacoOrder> findById(Long id);

    TacoOrder save(TacoOrder tacoOrder);

    TacoOrder update(TacoOrder tacoOrder);

    boolean remove(TacoOrder tacoOrder);
}
