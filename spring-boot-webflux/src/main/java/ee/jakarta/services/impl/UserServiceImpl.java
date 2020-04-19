package ee.jakarta.services.impl;

import ee.jakarta.entities.User;
import ee.jakarta.repositories.UserRepository;
import ee.jakarta.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> getUser(String email) {
        return userRepository.findById(email);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
