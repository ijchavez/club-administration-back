package uy.com.club.administration.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import uy.com.club.administration.domain.user.UserApp;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserApp, String> {
    Optional<UserApp> findByEmail(String mail);

    Optional<UserApp> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
