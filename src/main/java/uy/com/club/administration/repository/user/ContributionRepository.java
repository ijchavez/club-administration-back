package uy.com.club.administration.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uy.com.club.administration.domain.Contribution;


import java.util.Date;
import java.util.List;

public interface ContributionRepository extends MongoRepository<Contribution, String> {

    List<Contribution> findByPartner_Id(String id);

    @Query("{$expr:{$and:[{$eq:[{$year:'$date'}, ?0]}, {$eq:[{$month:'$date'}, ?1]}]}}")
    List<Contribution> findByContributionQuery(int year, int month);

    Page<Contribution> findByPartner_Name(String name, Pageable pageable);

    List<Contribution> findByPartner_IdAndDate(String parentId, Date date);

    List<Contribution> findByPaymentMethodAndDate(String payMethod, Date date);

    List<Contribution> findByPaymentMethodAndPartner_Id(String payMethod, String parentId);

    List<Contribution> findByPaymentMethodAndPartner_IdAndDate(String payMethod, String parentId, Date date);
}