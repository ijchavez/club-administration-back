package uy.com.club.administration.mappers;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uy.com.club.administration.domain.Member;
import uy.com.club.administration.dto.request.MemberDTO;
import uy.com.club.administration.dto.request.MemberListDTO;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDTO memberToMemberDTO(Member member);

    @AfterMapping
    default void calculateFullName(Member member, @MappingTarget MemberDTO dto) {
        dto.setFullName(member.getFirstName().concat(" ").concat(member.getLastName()));
    }

    List<MemberDTO> mapMemberToList(List<Member> memberList);

    Member memberDTOToMember(MemberDTO memberDTO);
}