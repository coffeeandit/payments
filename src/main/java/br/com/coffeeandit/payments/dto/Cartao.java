package br.com.coffeeandit.payments.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cartao {

    private String numero;
    private int mesExpiracao;
    private int anoExpiracao;
    private String cvc;
}
