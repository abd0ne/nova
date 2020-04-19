package ee.jakarta.controllers;

import ee.jakarta.entities.User;
import ee.jakarta.services.IUserService;
import ee.jakarta.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping(Constants.USER_CONTROLLER_PATH)
public class UserRestController {
    private final IUserService userService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<User>> saveUser(@RequestBody User user){
        return ResponseEntity.ok(userService.saveUser(user));
    }
}
