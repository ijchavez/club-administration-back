package uy.com.club.administration.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contribution {
    @Id
    private String id;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private Date date;
    private Issuer issuer;
    private Partner partner;
}