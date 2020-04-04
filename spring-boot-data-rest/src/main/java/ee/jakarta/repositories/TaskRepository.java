package ee.jakarta.repositories;


import ee.jakarta.entities.Task;
import ee.jakarta.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
