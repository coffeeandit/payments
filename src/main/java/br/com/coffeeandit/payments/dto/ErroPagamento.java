package br.com.coffeeandit.payments.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroPagamento {
    private String message;
    private Long timestamp = System.currentTimeMillis();
}
