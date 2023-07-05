package uy.com.club.administration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributionSuggested {
    @Id
    private String id;
    private PaymentMethod paymentMethod;
    private CategoryType categoryType;
    private BigDecimal amount;
}