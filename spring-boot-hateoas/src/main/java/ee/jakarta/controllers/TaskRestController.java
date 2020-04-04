package ee.jakarta.controllers;

import ee.jakarta.entities.Task;
import ee.jakarta.repositories.TaskRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tasks")
public class TaskRestController {

    public static final String TASKS = "tasks";
    private final TaskRepository taskRepository;

    public TaskRestController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Task>>> findAll() {

        List<EntityModel<Task>> tasks = StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .map(task -> new EntityModel<>(task,
                        linkTo(methodOn(TaskRestController.class).findOne(task.getId())).withSelfRel(),
                        linkTo(methodOn(TaskRestController.class).findAll()).withRel(TASKS)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CollectionModel<>(tasks, linkTo(methodOn(TaskRestController.class).findAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Task>> findOne(@PathVariable long id) {

        return taskRepository.findById(id)
                .map(task -> new EntityModel<>(task,
                        linkTo(methodOn(TaskRestController.class).findOne(task.getId())).withSelfRel(),
                        linkTo(methodOn(TaskRestController.class).findAll()).withRel(TASKS)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
