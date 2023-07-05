package uy.com.club.administration.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uy.com.club.administration.domain.Partner;


public interface PartnerRepository extends MongoRepository<Partner, String>, PartnerRepositoryCustom {

    @Query(value = "{'$and':[ {'name':{$regex: ?0, $options: 'i' }},{'active':true} ]}")
    Page<Partner> findByMethod(String name, Pageable pageable);

    @Query(value = "{'active':true}")
    Page<Partner> findByActive(Pageable pageable);
}