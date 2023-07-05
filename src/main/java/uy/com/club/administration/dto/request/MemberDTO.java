package uy.com.club.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uy.com.club.administration.domain.CategoryType;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private String id;
    private Date startDate;
    private String firstName;
    private String fullName;
    private String lastName;
    private String document;
    private CategoryType categoryType;
    private String partnerId;
    private boolean active;
}
