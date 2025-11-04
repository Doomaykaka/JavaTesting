package taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.Ingredient;

public interface JdbcTemplateOrderRepository extends CrudRepository<Ingredient, Long> {
}