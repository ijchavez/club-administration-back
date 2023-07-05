package uy.com.club.administration.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uy.com.club.administration.domain.ContributionSuggested;
import uy.com.club.administration.dto.request.ContributionSuggestedDTO;

@Mapper
public interface ContributionSuggestedMapper {
    ContributionSuggestedMapper INSTANCE = Mappers.getMapper(ContributionSuggestedMapper.class);

    ContributionSuggestedDTO contributionSuggestedToDTO(ContributionSuggested contributionSuggested);

    ContributionSuggested DTOToContributionSuggested(ContributionSuggestedDTO contributionSuggestedDTO);
}