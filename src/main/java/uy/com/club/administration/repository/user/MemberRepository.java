package uy.com.club.administration.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uy.com.club.administration.domain.Member;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String>, MemberRepositoryCustom {
    List<Member> findByIdIn(List<String> ids);

    @Query(value = "{'$and':[{'$or':[ {'firstName':{$regex: ?0, $options: 'i' }}, {'lastName':{$regex: ?0 , $options: 'i'}}, {'document':{$regex: ?0, $options: 'i' }} ]},{'active':true},{'$or':[{ 'partnerId':{$exists:false}},{'partnerId':null}]}]}")
    Page<Member> findByMethod(String term, Pageable pageable);

    @Query(value = "{'active':true}")
    Page<Member> findByActive(Pageable pageable);
}