package ee.jakarta.controllers;

import ee.jakarta.Constants;
import ee.jakarta.assemblers.TaskModelAssembler;
import ee.jakarta.entities.Task;
import ee.jakarta.models.TaskModel;
import ee.jakarta.services.impl.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.TASK_URI)
@AllArgsConstructor
public class TaskRestController {

    private final TaskService taskService;
    private final TaskModelAssembler taskModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<TaskModel>> findAll() {
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(taskModelAssembler.toCollectionModel(tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> findOne(@PathVariable long id) {
        return taskService.findById(id)
                .map(taskModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
