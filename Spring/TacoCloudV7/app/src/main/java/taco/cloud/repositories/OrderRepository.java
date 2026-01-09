package taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}
