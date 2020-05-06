package ee.jakarta.services;

import ee.jakarta.entities.User;
import ee.jakarta.models.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserService {
     List<User> findAll();
     UserModel findById(long id) throws Exception;
}
