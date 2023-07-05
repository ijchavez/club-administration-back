package uy.com.club.administration.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uy.com.club.administration.domain.CategoryType;
import uy.com.club.administration.domain.Issuer;
import uy.com.club.administration.domain.Partner;
import uy.com.club.administration.domain.PaymentMethod;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributionDTO {
    private String id;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private Date date;
    private Issuer issuer;
    private Partner partner;
}