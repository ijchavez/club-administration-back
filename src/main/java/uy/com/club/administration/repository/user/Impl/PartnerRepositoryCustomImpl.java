package uy.com.club.administration.repository.user.Impl;

import lombok.RequiredArgsConstructor;
import org.bson.BasicBSONObject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import uy.com.club.administration.domain.Partner;
import uy.com.club.administration.repository.user.PartnerRepositoryCustom;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
public class PartnerRepositoryCustomImpl implements PartnerRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteMember(String id, String memberId) {
        mongoTemplate.updateFirst(
                query(where("id").is(id)),
                new Update().pull("members", new BasicBSONObject("_id", new ObjectId(memberId))),
                Partner.class
        );
    }
}
