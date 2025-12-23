package taco.cloud.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import taco.cloud.models.TacoOrder;

public interface OrderRepository extends MongoRepository<TacoOrder, String> {
}
