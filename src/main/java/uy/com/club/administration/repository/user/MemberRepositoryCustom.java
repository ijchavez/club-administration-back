package uy.com.club.administration.repository.user;


import java.util.List;

public interface MemberRepositoryCustom {
    void unJoinMembers(List<String> members);
}
