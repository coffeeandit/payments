package br.com.coffeeandit.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cartao {
    @Schema(description = "Número de Cartão")
    @NotBlank
    private String numero;
    @NotBlank
    @Schema(description = "Mes de Expiracao")
    private int mesExpiracao;
    @Schema(description = "Ano de Expiracao")
    @NotBlank
    private int anoExpiracao;
    @Schema(description = "CVC Validação de Cartão")
    @NotBlank
    private String cvc;
}
