package br.com.coffeeandit.payments.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDto {
    @Schema(description = "Estrutura de Cartão")
    @NotBlank
    private Cartao cartao;


    @Schema(description = "Moeda de Pagamentos EUR, USB, BRL")
    @NotBlank
    public enum Moeda {
        EUR, USD, BRL;
    }
    @Schema(description = "Estrutura de Cartão")
    @NotBlank
    private String descricao;
    @Schema(description = "Valor do Pagamento")
    private long valor;
    private Moeda moeda = Moeda.BRL;
}