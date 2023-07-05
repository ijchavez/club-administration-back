package uy.com.club.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.com.club.administration.domain.CategoryType;
import uy.com.club.administration.domain.Member;
import uy.com.club.administration.domain.Partner;
import uy.com.club.administration.dto.request.MemberDTO;
import uy.com.club.administration.dto.request.MemberListDTO;
import uy.com.club.administration.exception.ErrorConstants;
import uy.com.club.administration.exception.MissingMemberException;
import uy.com.club.administration.exception.MissingPartnerException;
import uy.com.club.administration.mappers.MemberMapper;
import uy.com.club.administration.repository.user.CategoryTypeRepository;
import uy.com.club.administration.repository.user.MemberRepository;
import uy.com.club.administration.repository.user.PartnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PartnerRepository partnerRepository;
    private final CategoryTypeRepository categoryTypeRepository;

    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = MemberMapper.INSTANCE.memberDTOToMember(memberDTO);
        updateCategoryType(memberDTO, member);
        if (memberDTO.getPartnerId() != null) {
            if (partnerRepository.findById(memberDTO.getPartnerId()).isPresent()) {
                memberRepository.save(member);
            } else {
                throw new MissingPartnerException();
            }
        } else {
            memberRepository.save(member);
        }
        return MemberMapper.INSTANCE.memberToMemberDTO(member);
    }

    public MemberDTO updateMember(String id, MemberDTO memberDTO) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            Member member = MemberMapper.INSTANCE.memberDTOToMember(memberDTO);
            updateCategoryType(memberDTO, member);
            member.setId(id);
            memberRepository.save(member);
            return MemberMapper.INSTANCE.memberToMemberDTO(member);
        }
        throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
    }

    private void updateCategoryType(MemberDTO memberDTO, Member member) {
        Optional<CategoryType> categoryOpt = categoryTypeRepository.findById(memberDTO.getCategoryType().getId());
        if (categoryOpt.isPresent()) {
            member.setCategoryType(categoryOpt.get());
        }
    }

    public MemberDTO getMember(String id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            return MemberMapper.INSTANCE.memberToMemberDTO(memberOpt.get());
        }
        throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
    }

    public List<MemberDTO> getAllMember() {
        List<Member> members = memberRepository.findAll();
        return MemberMapper.INSTANCE.mapMemberToList(members);
    }

    public Page<MemberDTO> findMemberByQuery(MemberDTO memberDTO, Pageable pageable) {
        Page<Member> member = getMembers(memberDTO, pageable);
        return member.map(MemberMapper.INSTANCE::memberToMemberDTO);
    }

    private Page<Member> getMembers(MemberDTO memberDTO, Pageable pageable) {
        if (memberDTO.getFirstName() != null) {
            return memberRepository.findByMethod(memberDTO.getFirstName(), pageable);
        }
        return memberRepository.findByActive(pageable);
    }

    public Member deleteMember(String id) {
        Optional<Member> memberOPt = memberRepository.findById(id);
        if (memberOPt.isPresent() && memberOPt.get().isActive()) {
            if (memberOPt.get().getPartnerId() != null) {
                partnerRepository.deleteMember(memberOPt.get().getPartnerId(), memberOPt.get().getId());
            }
            memberOPt.get().setPartnerId(null);
            memberOPt.get().setActive(false);
            return memberRepository.save(memberOPt.get());
        } else {
            throw new MissingMemberException(ErrorConstants.MISSING_MEMBER + " OR MEMBER DISABLED");
        }
    }

    public boolean isMemberExitInPartner(String id, List<Member> resultMember) {
        Optional<Partner> partnerOpt = partnerRepository.findById(id);
        if (partnerOpt.isPresent()) {
            for (Member m : partnerOpt.get().getMembers()) {
                if (m.getId().equals(id)) {
                    return true;
                } else {
                    resultMember.add(m);
                }
            }
            return false;
        }
        throw new MissingPartnerException("The Member already exits in this Partner");
    }

    public boolean isMemberHasPartner(String id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            return memberOpt.get().getPartnerId() != null;
        }
        throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
    }

    public MemberDTO joinMemberToPartner(MemberDTO memberDTO) {
        List<Member> resultMember = new ArrayList<>();
        Optional<Partner> partnerOpt = partnerRepository.findById(memberDTO.getPartnerId());
        Optional<Member> memberOpt = memberRepository.findById(memberDTO.getId());
        if (partnerOpt.isPresent()) {
            if (memberOpt.isPresent() && !isMemberExitInPartner(memberDTO.getPartnerId(), resultMember)) {
                resultMember.add(memberOpt.get());
                partnerOpt.get().setMembers(resultMember);
                if (!isMemberHasPartner(memberDTO.getId())) {
                    memberOpt.get().setPartnerId(memberDTO.getPartnerId());
                    partnerRepository.save(partnerOpt.get());
                    memberRepository.save(memberOpt.get());
                    return MemberMapper.INSTANCE.memberToMemberDTO(memberOpt.get());
                }
                throw new MissingPartnerException(ErrorConstants.MISSING_PARTNER);
            } else {
                throw new MissingMemberException(ErrorConstants.MISSING_MEMBER);
            }
        } else {

            throw new MissingPartnerException(ErrorConstants.MISSING_PARTNER);
        }
    }
}
