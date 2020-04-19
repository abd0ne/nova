package ee.jakarta.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static java.util.Collections.singletonList;

@EnableReactiveMongoRepositories(basePackages = "ee.jakarta.repositories")
@Configuration
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${mongoPort}")
    @Getter
    private String port;

    @Value("${mongoDatabaseName}")
    private String dbName;

    @Value("${mongoHost}")
    @Getter
    private String mongoHost;

    @Value("${mongoUsername}")
    @Getter
    private String mongoUsername;

    @Value("${mongoPassword}")
    @Getter
    private String mongoPassword;

    @Value("${mongoAuthenticationDatabase}")
    @Getter
    private String mongoAuthenticationDatabase;

    @Override
    public MongoClient reactiveMongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(
                        MongoCredential.createCredential(
                                getMongoUsername(),
                                getMongoAuthenticationDatabase(),
                                getMongoPassword().toCharArray()))
                .applyToClusterSettings(builder  -> {
                    builder.hosts(singletonList(new ServerAddress(getMongoHost(), Integer.parseInt(getPort()))));
                })
                .build();

        return MongoClients.create(settings);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}
