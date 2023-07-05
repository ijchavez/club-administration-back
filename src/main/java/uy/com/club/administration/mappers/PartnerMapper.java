package uy.com.club.administration.mappers;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import uy.com.club.administration.domain.Member;
import uy.com.club.administration.domain.Partner;
import uy.com.club.administration.dto.request.MemberDTO;
import uy.com.club.administration.dto.request.PartnerCreateDTO;
import uy.com.club.administration.dto.request.PartnerDTO;
import uy.com.club.administration.dto.request.PartnerListDTO;

import java.util.List;

@Mapper
public interface PartnerMapper {
    PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);

    PartnerListDTO partnerToListDTO(Partner partner);

    @Mapping(target = "members", ignore = true)
    Partner partnerDTOCreateToPartner(PartnerCreateDTO partner);

    @Mapping(target = "members", ignore = true)
    Partner partnerDTOToPartner(PartnerDTO partner);

    @Mapping(source = "members", target = "members")
    PartnerDTO partnerToDTO(Partner partner);

    @IterableMapping(qualifiedByName = "Member")
    List<MemberDTO> mapMemberToList(List<Member> members);

    @Named("Member")
    default MemberDTO mapMemberToString(Member member) {
        return MemberMapper.INSTANCE.memberToMemberDTO(member);
    }
}