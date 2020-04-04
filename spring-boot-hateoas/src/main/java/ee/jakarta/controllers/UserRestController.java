package ee.jakarta.controllers;

import ee.jakarta.assemblers.UserModelAssembler;
import ee.jakarta.entities.Task;
import ee.jakarta.entities.User;
import ee.jakarta.models.UserModel;
import ee.jakarta.repositories.TaskRepository;
import ee.jakarta.repositories.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserRestController {
    public static final String USERS = "users";
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserModelAssembler userModelAssembler;

    public UserRestController(UserRepository userRepository, TaskRepository taskRepository, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> findAll() {

        List<User> users = userRepository.findAll();

        return ResponseEntity.ok(userModelAssembler.userModels(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> findOne(@PathVariable long id) {

        return userRepository.findById(id)
                .map(user -> new EntityModel<>(user,
                        linkTo(methodOn(UserRestController.class).findOne(user.getId())).withSelfRel(),
                        linkTo(methodOn(UserRestController.class).findTasks(user.getId())).withRel("tasks")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<CollectionModel<EntityModel<Task>>> findTasks(@PathVariable long id) {
        User user = userRepository.findById(id).get();
        List<EntityModel<Task>> tasks = taskRepository.findByUser(user).stream()
                .map(task -> new EntityModel<>(task,
                        linkTo(methodOn(TaskRestController.class).findOne(task.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CollectionModel<>(tasks, linkTo(methodOn(UserRestController.class).findTasks(user.getId())).withSelfRel()));
    }
}
