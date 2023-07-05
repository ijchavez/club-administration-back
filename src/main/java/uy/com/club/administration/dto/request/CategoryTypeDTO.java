package uy.com.club.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTypeDTO {
    private String id;
    private String name;
    private boolean root;
    private String parentId;
}
