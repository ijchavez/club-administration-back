package uy.com.club.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributionListDTO {
    private String categoryTypeName;
    private String paymentMethodName;
    private BigDecimal amount;
    private Date date;
    private String issuerName;
    private String partnerName;
}