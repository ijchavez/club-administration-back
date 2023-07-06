package uy.com.club.administration.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {

    public static final WriteConcern WRITE_CONCERN = WriteConcern.JOURNALED;
    private @Autowired MongoClient mongoClient;
    private final String mongoUri;

    public MongoConfig(@Value("${spring.data.mongodb.uri}") String mongoUri) {
        this.mongoUri = mongoUri;
    }

    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();

        ConnectionString connectionString = new ConnectionString(mongoUri);

        MongoClientSettings settings =
                MongoClientSettings.builder()
                        .applyToConnectionPoolSettings(
                                builder -> {
                                    builder.maxWaitTime(120000, TimeUnit.MILLISECONDS).build();
                                })
                        .applyConnectionString(connectionString)
                        .writeConcern(WRITE_CONCERN)
                        .build();

        mongo.setMongoClientSettings(settings);
        mongo.setCredential(new MongoCredential[] {settings.getCredential()});
        return mongo;
    }

    @Primary
    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        SimpleMongoClientDatabaseFactory factory =
                new SimpleMongoClientDatabaseFactory(mongoClient, "club-administration");
        return new MongoTemplate(factory);
    }

    @Bean
    public GridFsTemplate secondaryFilesGridFsTemplate() {
        return new GridFsTemplate(mongoTemplate().getMongoDatabaseFactory(), mongoTemplate().getConverter());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
