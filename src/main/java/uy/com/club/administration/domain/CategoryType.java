package uy.com.club.administration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryType {
    @Id
    private String id;
    private String name;
    private boolean root;
    private CategoryType parent;
}
