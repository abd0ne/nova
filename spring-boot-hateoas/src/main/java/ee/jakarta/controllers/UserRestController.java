package ee.jakarta.controllers;

import ee.jakarta.Constants;
import ee.jakarta.assemblers.UserModelAssembler;
import ee.jakarta.entities.Task;
import ee.jakarta.entities.User;
import ee.jakarta.models.UserModel;
import ee.jakarta.services.impl.TaskService;
import ee.jakarta.services.impl.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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
    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @GetMapping
    public ResponseEntity<CollectionModel<UserModel>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(userModelAssembler.toCollectionModel(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserModel>> findOne(@PathVariable long id) {
        HttpStatus httpStatus = HttpStatus.OK;
        UserModel userModel = new UserModel();
        try {
            userModel = userService.findById(id);
        }catch (Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
            logger.error("User not found ...");
        }

        return ResponseEntity.status(httpStatus).body(new EntityModel<>(userModel));
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<CollectionModel<EntityModel<Task>>> findTasks(@PathVariable long id) {
        UserModel userModel;
        User user = null;
        try {
            userModel = userService.findById(id);
            user = new User(userModel.getUserId(), userModel.getName(), userModel.getFirstName(), userModel.getMail(),null);
        } catch (Exception e) {
           logger.error(e.getMessage());
        }
        List<EntityModel<Task>> tasks = taskService.findByUser(user).stream()
                .map(task -> new EntityModel<>(task,
                        linkTo(methodOn(TaskRestController.class).findOne(task.getId())).withSelfRel()))
                .collect(Collectors.toList());

        assert user != null;
        return ResponseEntity.ok(new CollectionModel<>(tasks, linkTo(methodOn(UserRestController.class).findTasks(user.getId())).withSelfRel()));
    }
}
