package uy.com.club.administration.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uy.com.club.administration.domain.Contribution;
import uy.com.club.administration.dto.request.ContributionDTO;
import uy.com.club.administration.dto.request.ContributionListDTO;

import java.util.List;

@Mapper
public interface ContributionMapper {
    ContributionMapper INSTANCE = Mappers.getMapper(ContributionMapper.class);
    List<ContributionListDTO> mapContributionToList(List<Contribution> contribution);

    ContributionDTO contributionToDTO(Contribution contribution);

    Contribution contributionDTOToContribution(ContributionDTO contributionDTO);

    @Mappings({
            @Mapping(source = "paymentMethod", target = "paymentMethodName"),
            @Mapping(source = "partner.categoryType.name", target = "categoryTypeName"),
            @Mapping(source = "partner.name", target = "partnerName"),
            @Mapping(source = "issuer.name", target = "issuerName")
    })
    ContributionListDTO mapContributionToLists(Contribution contribution);

}
