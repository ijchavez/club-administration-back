package uy.com.club.administration.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventChangeMember {
    @Id
    private String id;
    private Member member;
    private CategoryType changed;
    private String reason;
    private Date date;
}
