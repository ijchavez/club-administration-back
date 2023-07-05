package uy.com.club.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.com.club.administration.domain.Contribution;
import uy.com.club.administration.domain.Partner;
import uy.com.club.administration.dto.request.ContributionDTO;
import uy.com.club.administration.dto.request.ContributionListDTO;
import uy.com.club.administration.exception.*;
import uy.com.club.administration.mappers.ContributionMapper;
import uy.com.club.administration.repository.user.ContributionRepository;
import uy.com.club.administration.repository.user.PartnerRepository;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContributionService {
    private final ContributionRepository contributionRepository;
    private final PartnerRepository partnerRepository;

    public void createContribution(ContributionDTO contributionDTO) throws ParseException {

        Optional<Partner> optionalPartner = partnerRepository.findById(contributionDTO.getPartner().getId());
        Contribution contribution = ContributionMapper.INSTANCE.contributionDTOToContribution(contributionDTO);
        if (optionalPartner.isPresent()) {
            contribution.setPartner(optionalPartner.get());
            contributionRepository.save(contribution);
        } else {
            throw new MissingPartnerException(ErrorConstants.MISSING_PARTNER);
        }
    }

    public ContributionDTO getContribution(String id) {
        Optional<Contribution> contributionOpt = contributionRepository.findById(id);
        if (contributionOpt.isPresent()) {
            return ContributionMapper.INSTANCE.contributionToDTO(contributionOpt.get());
        }
        throw new MissingContributionException(ErrorConstants.MISSING_CONTRIBUTION);
    }

    public List<Contribution> getContributionMonth(int year, int month) {
        return contributionRepository.findByContributionQuery(year, month);
    }

    public List<ContributionDTO> getAllContribution() {
        List<Contribution> contributions = contributionRepository.findAll();
        return contributions.stream()
                .map(ContributionMapper.INSTANCE::contributionToDTO)
                .collect(Collectors.toList());
    }

    public List<ContributionDTO> getContributionByPartnerId(String partnerId) {
        List<Contribution> listContOpt = contributionRepository.findByPartner_Id(partnerId);
        if (!listContOpt.isEmpty()) {
            return listContOpt.stream()
                    .map(ContributionMapper.INSTANCE::contributionToDTO)
                    .collect(Collectors.toList());
        }
        throw new MissingContributionException(ErrorConstants.MISSING_CONTRIBUTION);
    }

    public ContributionDTO updateContribution(String id, ContributionDTO contributionDTO) {
        Optional<Contribution> contOpt = contributionRepository.findById(id);
        if (contOpt.isPresent()) {
            Contribution contribution = contOpt.get();
            contribution.setAmount(contributionDTO.getAmount());
            return ContributionMapper.INSTANCE.contributionToDTO(contributionRepository.save(contribution));
        } else {
            throw new MissingContributionException(ErrorConstants.MISSING_CONTRIBUTION);
        }
    }

    public List<ContributionListDTO> listAll() {
        List<ContributionListDTO> contributionListDTOS = new ArrayList<>();
        List<Contribution> contributionList = contributionRepository.findAll();
        for (Contribution contribution : contributionList) {
            contributionListDTOS.add(ContributionMapper.INSTANCE.mapContributionToLists(contribution));
        }
        return contributionListDTOS;
    }

    public void deleteContribution(String id) {
        Optional<Contribution> contributionOpt = contributionRepository.findById(id);
        if (contributionOpt.isPresent()) {
            contributionRepository.deleteById(id);
        } else {
            throw new MissingContributionException(ErrorConstants.MISSING_CONTRIBUTION);
        }
    }

    public Page<ContributionDTO> findAll(Pageable pageable) {
        Page<Contribution> contPage = contributionRepository.findAll(pageable);
        return contPage.map(ContributionMapper.INSTANCE::contributionToDTO);
    }

    public Page<ContributionDTO> findContributionByQuery(ContributionDTO contributionDTO, Pageable pageable) {
        Page<Contribution> contributions = contributionRepository.findByPartner_Name(contributionDTO.getPartner().getName(), pageable);
        return contributions.map(ContributionMapper.INSTANCE::contributionToDTO);
    }

    public List<Contribution> findContributionsByFilters(String payMethod, String parentId, String date) throws ParseException {
        return null;
    }
}