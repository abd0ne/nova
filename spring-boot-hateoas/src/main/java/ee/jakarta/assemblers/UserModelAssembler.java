package ee.jakarta.assemblers;

import ee.jakarta.controllers.UserRestController;
import ee.jakarta.entities.User;
import ee.jakarta.models.UserModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
        userModel.setFirstName(user.getName());
        userModel.setMail(user.getEmail());
        userModel.setName(user.getName());
        userModel.setUserId(user.getId());
        userModel.add(linkTo(methodOn(UserRestController.class).findOne(user.getId())).withSelfRel());
        return userModel;
    }

   public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> users) {
        CollectionModel<UserModel> userModels = super.toCollectionModel(users);
        userModels.add(linkTo(methodOn(UserRestController.class).findAll()).withSelfRel());
        return userModels;
    }
}
