package ee.jakarta.controllers;

import ee.jakarta.Constants;
import ee.jakarta.assemblers.UserModelAssembler;
import ee.jakarta.entities.Task;
import ee.jakarta.entities.User;
import ee.jakarta.models.UserModel;
import ee.jakarta.services.impl.TaskService;
import ee.jakarta.services.impl.UserService;
import lombok.AllArgsConstructor;
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
@RequestMapping(Constants.USER_URI)
@AllArgsConstructor()
public class UserRestController {
    private final UserService userService;
    private final TaskService taskService;
    private final UserModelAssembler userModelAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<UserModel>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(userModelAssembler.toCollectionModel(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findOne(@PathVariable long id) {
        return userService.findById(id)
                .map(userModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<CollectionModel<EntityModel<Task>>> findTasks(@PathVariable long id) {
        User user = userService.findById(id).get();
        List<EntityModel<Task>> tasks = taskService.findByUser(user).stream()
                .map(task -> new EntityModel<>(task,
                        linkTo(methodOn(TaskRestController.class).findOne(task.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CollectionModel<>(tasks, linkTo(methodOn(UserRestController.class).findTasks(user.getId())).withSelfRel()));
    }
}
