package uy.com.club.administration.repository.user.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import uy.com.club.administration.domain.Member;
import uy.com.club.administration.repository.user.MemberRepositoryCustom;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void unJoinMembers(List<String> members) {
        mongoTemplate.updateMulti(
                query(where("_id").in(members)),
                Update.update("partnerId",null),
                Member.class
        );
    }
}
