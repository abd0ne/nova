package ee.jakarta.services;

import ee.jakarta.entities.Task;
import ee.jakarta.entities.User;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    List<Task> findByUser(User user);
    List<Task> findAll();
    Optional<Task> findById(long id);
}
