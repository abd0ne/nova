package ee.jakarta;

import ee.jakarta.entities.User;
import ee.jakarta.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMongodbApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserRepository userRepository){
        List<User> users = new ArrayList<>();
        users.add(new User("email@email.com","Name","FirstName"));
        users.add(new User("email1@email.com","Name1","FirstName1"));
        users.add(new User("email2@email.com","Name2","FirstName2"));
        users.add(new User("email3@email.com","Name3","FirstName3"));

        return args -> {
            users.forEach(userRepository::save);
        };
    }

}
