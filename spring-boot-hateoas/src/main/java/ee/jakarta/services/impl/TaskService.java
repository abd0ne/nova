package ee.jakarta.services.impl;

import ee.jakarta.entities.Task;
import ee.jakarta.entities.User;
import ee.jakarta.repositories.TaskRepository;
import ee.jakarta.services.ITaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(long id) {
        return taskRepository.findById(id);
    }
}
