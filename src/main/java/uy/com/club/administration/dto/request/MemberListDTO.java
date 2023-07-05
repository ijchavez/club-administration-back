package uy.com.club.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberListDTO {
    private String id;
    private Date startDate;
    private String firstName;
    private String lastName;
    private String document;
    private String categoryType;
    private String categoryTypeId;
    private String partnerId;
    private boolean active;
}