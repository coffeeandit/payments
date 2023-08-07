package br.com.coffeeandit.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoStripe {
    @Schema(description = "Identificador do pagamento")
    private String id;
    @Schema(description = "Método de Pagamento")
    private String paymentMethod;
    @Schema(description = "Status de Pagamento")
    private String status;
    @Schema(description = "Data de criação")
    private LocalDateTime created;
    @Schema(description = "Token de cartão criado.")
    private String cardToken;
    @Schema(description = "Valor do Pagamento.")
    private BigDecimal valor;
    @Schema(description = "URL para acesso via Browser")
    private String receiptUrl;

}
