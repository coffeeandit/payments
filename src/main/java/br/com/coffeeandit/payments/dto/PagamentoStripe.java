package br.com.coffeeandit.payments.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoStripe {
    private String id;
    private String paymentMethod;
    private String status;
    private LocalDateTime created;
    private String cardToken;
    BigDecimal valor;

}
