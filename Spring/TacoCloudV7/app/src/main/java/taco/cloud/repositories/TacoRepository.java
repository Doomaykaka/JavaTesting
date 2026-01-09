package taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {

}
