package uy.com.club.administration.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import uy.com.club.administration.domain.user.ERole;
import uy.com.club.administration.domain.user.Role;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
