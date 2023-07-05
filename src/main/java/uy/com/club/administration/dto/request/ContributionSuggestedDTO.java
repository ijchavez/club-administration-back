package uy.com.club.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uy.com.club.administration.domain.CategoryType;
import uy.com.club.administration.domain.PaymentMethod;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributionSuggestedDTO {
    private String id;
    private PaymentMethod paymentMethod;
    private CategoryType categoryType;
    private BigDecimal amount;
}