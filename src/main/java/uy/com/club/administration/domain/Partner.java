package uy.com.club.administration.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    @Id
    private String id;
    private String name;
    private CategoryType categoryType;
    private boolean active;
    private boolean paymentRequired;
    private List<Member> members;
}