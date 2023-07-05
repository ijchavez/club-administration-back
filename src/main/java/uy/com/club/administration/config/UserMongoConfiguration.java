package uy.com.club.administration.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "uy.com.club.administration.repository.user")
public class UserMongoConfiguration {
}
