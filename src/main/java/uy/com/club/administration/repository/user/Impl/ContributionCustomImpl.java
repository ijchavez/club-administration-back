package uy.com.club.administration.repository.user.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import uy.com.club.administration.repository.user.ContributionCustom;


@RequiredArgsConstructor
public class ContributionCustomImpl implements ContributionCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void getContributionByFilters(String categoryTypeId) {
//        return mongoTemplate.find(Contribution.class, categoryTypeId);
    }
}