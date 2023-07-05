package uy.com.club.administration.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uy.com.club.administration.domain.ContributionSuggested;

import java.util.Optional;


public interface ContributionSuggestedRepository extends MongoRepository<ContributionSuggested, String> {

    @Query(value = "{'id': ?0}")
    Page<ContributionSuggested> findByMethod(String id, Pageable pageable);

    Optional<ContributionSuggested> findFirstByCategoryType_IdAndPaymentMethod(String categoryId, String paymentMethod);
}