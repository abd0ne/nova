package ee.jakarta.services;

import ee.jakarta.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
     List<User> findAll();
     Optional<User> findById(long id);
}
