package ee.jakarta.assemblers;

import ee.jakarta.controllers.UserRestController;
import ee.jakarta.entities.User;
import ee.jakarta.models.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {
    public UserModelAssembler() {
        super(UserRestController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User user) {
        UserModel userModel = instantiateModel(user);
        userModel.add(linkTo(methodOn(UserRestController.class).findOne(user.getId())).withSelfRel());
        userModel.setFirstName(user.getName());
        userModel.setMail(user.getEmail());
        userModel.setName(user.getName());
        userModel.setUserId(user.getId());
        return userModel;
    }

    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> users)
    {
        CollectionModel<UserModel> userModels = super.toCollectionModel(users);
        userModels.add(linkTo(methodOn(UserRestController.class).findAll()).withSelfRel());
        return userModels;
    }

    public List<UserModel> userModels(List<User> users) {
        if (users.isEmpty())
            return Collections.emptyList();

        return users.stream()
                .map(user -> UserModel.builder()
                        .userId(user.getId())
                        .firstName(user.getFirstName())
                        .name(user.getName())
                        .mail(user.getEmail())
                        .build()
                        .add(linkTo(methodOn(UserRestController.class).findAll()).withSelfRel(),
                                linkTo(methodOn(UserRestController.class).findTasks(user.getId())).withRel("tasks")))
                .collect(Collectors.toList());
    }
}
