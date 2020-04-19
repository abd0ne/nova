package ee.jakarta.repositories;

import ee.jakarta.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface UserRepository extends MongoRepository<User, String> {
}
