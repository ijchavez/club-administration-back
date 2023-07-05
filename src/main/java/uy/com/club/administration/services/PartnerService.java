package uy.com.club.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.com.club.administration.domain.CategoryType;
import uy.com.club.administration.domain.Member;
import uy.com.club.administration.domain.Partner;
import uy.com.club.administration.dto.request.MemberDTO;
import uy.com.club.administration.dto.request.PartnerCreateDTO;
import uy.com.club.administration.dto.request.PartnerDTO;
import uy.com.club.administration.dto.request.PartnerListDTO;
import uy.com.club.administration.exception.ErrorConstants;
import uy.com.club.administration.exception.MissingMemberException;
import uy.com.club.administration.exception.MissingPartnerException;
import uy.com.club.administration.mappers.PartnerMapper;
import uy.com.club.administration.repository.user.CategoryTypeRepository;
import uy.com.club.administration.repository.user.MemberRepository;
import uy.com.club.administration.repository.user.PartnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;
    private final CategoryTypeRepository categoryTypeRepository;
    private final MemberRepository memberRepository;

    public PartnerDTO createPartner(PartnerCreateDTO partnerDTO) {
        Partner partner = PartnerMapper.INSTANCE.partnerDTOCreateToPartner(partnerDTO);
        partner.setMembers(getMembersById(partnerDTO.getMembers()));
        partnerRepository.save(partner);
        updateCategoryType(partnerDTO, partner);
        updateMembers(partnerDTO.getMembers(), partner.getId());
        return PartnerMapper.INSTANCE.partnerToDTO(partner);
    }

    private List<Member> getMembersById(List<String> memberId) {
        List<Member> memberList = new ArrayList<>();
        for (String id : memberId) {
            Optional<Member> optionalMember = memberRepository.findById(id);
            if (optionalMember.isPresent()) {
                memberList.add(optionalMember.get());
            } else {
                throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
            }
        }
        return memberList;
    }

    private void updateMembers(List<String> membersIds, String id) {
        for (String memberId : membersIds) {
            Optional<Member> optionalMember = memberRepository.findById(memberId);
            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                member.setPartnerId(id);
                memberRepository.save(member);
            } else {
                throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
            }
        }
    }

    private List<String> getMembersToString(List<Member> members) {
        return members.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
    }

    public void updatePartner(String id, PartnerCreateDTO partnerDTO) {
        Optional<Partner> partnerOptional = partnerRepository.findById(id);
        if (partnerOptional.isPresent()) {
            resetMembers(partnerOptional.get().getMembers());
            Partner partner = PartnerMapper.INSTANCE.partnerDTOCreateToPartner(partnerDTO);
            partner.setId(id);
            updateCategoryType(partnerDTO, partner);
            updateMembers(partnerDTO.getMembers(), partner.getId());
            partner.setMembers(getMembersById(partnerDTO.getMembers()));
            partnerRepository.save(partner);
        } else {
            throw new MissingPartnerException(ErrorConstants.MISSING_PARTNER);
        }
    }

    private void resetMembers(List<Member> members) {
        updateMembers(getMembersToString(members), null);
    }

    private void updateCategoryType(PartnerCreateDTO partnerCreateDTO, Partner partner) {
        Optional<CategoryType> categoryOpt = categoryTypeRepository.findById(partnerCreateDTO.getCategoryType().getId());
        if (categoryOpt.isPresent()) {
            partner.setCategoryType(categoryOpt.get());
        }
    }

    public PartnerDTO getPartner(String id) {
        Optional<Partner> partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isPresent()) {
            return PartnerMapper.INSTANCE.partnerToDTO(partnerOpt.get());
        }
        throw new MissingPartnerException(ErrorConstants.MISSING_PARTNER);
    }

    public Page<PartnerListDTO> findAllPageable(PartnerDTO partnerDTO, Pageable pageable) {
        Page<Partner> partnerPage = getPartners(partnerDTO, pageable);
        return partnerPage.map(PartnerMapper.INSTANCE::partnerToListDTO);
    }

    public List<PartnerDTO> getAllPartner() {
        List<Partner> partners = partnerRepository.findAll();
        return partners.stream()
                .map(PartnerMapper.INSTANCE::partnerToDTO)
                .collect(Collectors.toList());
    }

    public Page<PartnerDTO> findPartnerByQuery(PartnerDTO partnerDTO, Pageable pageable) {
        Page<Partner> partnerPage = getPartners(partnerDTO, pageable);
        return partnerPage.map(PartnerMapper.INSTANCE::partnerToDTO);
    }

    private Page<Partner> getPartners(PartnerDTO partnerDTO, Pageable pageable) {
        if (partnerDTO.getName() != null) {
            return partnerRepository.findByMethod(partnerDTO.getName(), pageable);
        }
        return partnerRepository.findByActive(pageable);
    }

    public void deletePartner(String id) {
        Optional<Partner> partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isPresent()) {
            if (partnerOpt.get().getMembers() != null && !partnerOpt.get().getMembers().isEmpty()) {
                List<String> membersIds = partnerOpt.get().getMembers().stream().map(Member::getId).collect(Collectors.toList());
                memberRepository.unJoinMembers(membersIds);
            }
            partnerRepository.deleteById(id);
        } else {
            throw new MissingPartnerException(ErrorConstants.MISSING_PARTNER);
        }
    }

    public Partner updateIsMemberExitInPartner(Partner partner, String memberId) {
        List<Member> idMembers = new ArrayList<>();
        for (Member m : partner.getMembers()) {
            if (!m.getId().equals(memberId)) {
                idMembers.add(m);
            } else {
                Optional<Member> memberOpt = memberRepository.findById(memberId);
                if (memberOpt.isPresent()) {
                    memberOpt.get().setPartnerId(null);
                    memberRepository.save(memberOpt.get());
                } else {
                    throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
                }
            }
        }
        if (idMembers.equals(partner.getMembers())) {
            throw new MissingPartnerException("The Member not exits in this Partner");
        } else {
            if (idMembers.isEmpty()) {
                partner.setMembers(new ArrayList<>());
            } else {
                partner.setMembers(idMembers);
            }
            return partnerRepository.save(partner);
        }
    }

    public boolean isPartnerHasMember(String id) {
        Optional<Partner> partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isPresent()) {
            if (!partnerOpt.get().getMembers().isEmpty()) {
                return true;
            }
        } else {
            throw new MissingPartnerException(ErrorConstants.MISSING_PARTNER);
        }
        throw new MissingPartnerException("The Member not exits in this Partner");
    }

    public PartnerDTO leaveMemberFromPartner(String partnerId, String memberId) {
        Optional<Partner> partnerOpt = partnerRepository.findById(partnerId);
        if (partnerOpt.isPresent() && isPartnerHasMember(partnerId)) {
            return PartnerMapper.INSTANCE.partnerToDTO(updateIsMemberExitInPartner(partnerOpt.get(), memberId));

        } else {
            throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
        }
    }
}