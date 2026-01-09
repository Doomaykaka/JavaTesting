package taco.cloud.repositories;

import org.springframework.data.repository.CrudRepository;

import taco.cloud.models.UserData;

public interface UserRepository extends CrudRepository<UserData, Long>{
    UserData findByUsername(String username);
}
