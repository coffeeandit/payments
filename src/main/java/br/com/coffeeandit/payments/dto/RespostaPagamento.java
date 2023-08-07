package br.com.coffeeandit.payments.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespostaPagamento {
    private String id;
    private Long timestamp;
    private String paymentMethod;
    private String status;


}
