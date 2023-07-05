package uy.com.club.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uy.com.club.administration.domain.CategoryType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerListDTO {
    private String id;
    private String name;
    private CategoryType categoryType;
    private List<MemberDTO> members;
    private boolean active;
}