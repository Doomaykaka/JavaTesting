package taco.cloud.repositories;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.TacoOrder;

@Profile("prod")
public interface OrderRepository extends CrudRepository<TacoOrder, String> {
}
