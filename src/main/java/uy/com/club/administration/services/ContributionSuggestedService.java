package uy.com.club.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uy.com.club.administration.domain.CategoryType;
import uy.com.club.administration.domain.ContributionSuggested;
import uy.com.club.administration.dto.request.ContributionSuggestedDTO;
import uy.com.club.administration.exception.*;
import uy.com.club.administration.mappers.ContributionSuggestedMapper;
import uy.com.club.administration.repository.user.CategoryTypeRepository;
import uy.com.club.administration.repository.user.ContributionSuggestedRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContributionSuggestedService {
    private final ContributionSuggestedRepository contributionSuggestedRepository;
    private final CategoryTypeRepository categoryTypeRepository;

    public Page<ContributionSuggested> findAll(Pageable pageable) {
        return contributionSuggestedRepository.findAll(pageable);
    }

    public ContributionSuggestedDTO createContributionSuggested(ContributionSuggestedDTO contributionSuggestedDTO) {
        ContributionSuggested contributionSuggested = ContributionSuggestedMapper.INSTANCE.DTOToContributionSuggested(contributionSuggestedDTO);
        if (isExistCategoryType(contributionSuggestedDTO.getCategoryType().getId()).isPresent()) {
            updateCategoryType(contributionSuggested, contributionSuggestedDTO);
            contributionSuggestedRepository.save(contributionSuggested);
            return ContributionSuggestedMapper.INSTANCE.contributionSuggestedToDTO(contributionSuggested);
        } else {
            throw new MissingCategoryTypeException(ErrorConstants.MISSING_CATEGORY_TYPE);
        }
    }

    public Optional<CategoryType> isExistCategoryType(String id) {
        return categoryTypeRepository.findById(id);
    }

    public void updateCategoryType(ContributionSuggested contributionSuggested, ContributionSuggestedDTO contributionSuggestedDTO) {

        if (isExistCategoryType(contributionSuggestedDTO.getCategoryType().getId()).isPresent()) {
            contributionSuggested.setCategoryType(categoryTypeRepository.findById(contributionSuggestedDTO.getCategoryType().getId()).get());
            contributionSuggested.setAmount(contributionSuggestedDTO.getAmount());
        } else {
            throw new MissingCategoryTypeException(ErrorConstants.MISSING_CATEGORY_TYPE);
        }
    }

    public ContributionSuggestedDTO updateContributionSuggested(String id, ContributionSuggestedDTO contributionSuggestedDTO) {

        Optional<ContributionSuggested> contSuggestedOpt = contributionSuggestedRepository.findById(id);
        if (contSuggestedOpt.isPresent()) {
            ContributionSuggested contributionSuggested = contSuggestedOpt.get();
            updateCategoryType(contributionSuggested, contributionSuggestedDTO);
            contributionSuggestedRepository.save(contributionSuggested);
            return ContributionSuggestedMapper.INSTANCE.contributionSuggestedToDTO(contributionSuggested);
        }
        throw new MissingContributionSuggestedException(ErrorConstants.MISSING_CONTRIBUTION_SUGGESTED);
    }

    public ContributionSuggestedDTO getContributionSuggested(String id) {
        Optional<ContributionSuggested> contSuggestedOpt = contributionSuggestedRepository.findById(id);
        if (contSuggestedOpt.isPresent()) {
            return ContributionSuggestedMapper.INSTANCE.contributionSuggestedToDTO(contSuggestedOpt.get());
        }
        throw new MissingMemberException(ErrorConstants.MISSING_CONTRIBUTION_SUGGESTED);
    }

    public List<ContributionSuggestedDTO> getAllContributionsSuggested() {
        List<ContributionSuggested> contSuggestedOpt = contributionSuggestedRepository.findAll();
        return contSuggestedOpt.stream()
                .map(ContributionSuggestedMapper.INSTANCE::contributionSuggestedToDTO)
                .collect(Collectors.toList());
    }

    public Page<ContributionSuggestedDTO> findMemberByQuery(ContributionSuggestedDTO contributionSuDTO, Pageable pageable) {
        Page<ContributionSuggested> contributionSuggestedOptional = contributionSuggestedRepository.findByMethod(contributionSuDTO.getId(), pageable);
        return contributionSuggestedOptional.map(ContributionSuggestedMapper.INSTANCE::contributionSuggestedToDTO);
    }

    public void deleteContributionSuggested(String id) {
        contributionSuggestedRepository.deleteById(id);
    }

    public ContributionSuggestedDTO searchContribution(String categoryId, String paymentMethod) {
        Optional<ContributionSuggested> contributionSuggested = contributionSuggestedRepository.findFirstByCategoryType_IdAndPaymentMethod(categoryId, paymentMethod);
        if (contributionSuggested.isPresent()) {
            return ContributionSuggestedMapper.INSTANCE.contributionSuggestedToDTO(contributionSuggested.get());
        } else {
            throw new MissingMemberException(ErrorConstants.MISSING_CONTRIBUTION_SUGGESTED);
        }
    }
}