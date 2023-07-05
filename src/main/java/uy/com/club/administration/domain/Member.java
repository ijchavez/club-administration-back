package uy.com.club.administration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private String id;
    private Date startDate;
    private String firstName;
    private String lastName;
    private String document;
    private CategoryType categoryType;
    private String partnerId;
    private boolean active;
}
