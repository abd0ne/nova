package ee.jakarta.services;

import ee.jakarta.entities.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> saveUser(User user);
    Mono<User> getUser(String email);
    Flux<User> getAllUsers();
}
