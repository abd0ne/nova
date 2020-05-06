package ee.jakarta.services.impl;

import ee.jakarta.assemblers.UserModelAssembler;
import ee.jakarta.entities.User;
import ee.jakarta.models.UserModel;
import ee.jakarta.repositories.UserRepository;
import ee.jakarta.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserModelAssembler userModelAssembler;
    private final UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public UserModel findById(long id) throws Exception {
        return userRepository.findById(id).map(userModelAssembler::toModel).orElseThrow(Exception::new);
    }
}
