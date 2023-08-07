package br.com.coffeeandit.payments.controller;

import br.com.coffeeandit.payments.config.SecurityConfiguration;
import br.com.coffeeandit.payments.dto.PagamentoDto;
import br.com.coffeeandit.payments.dto.PagamentoStripe;
import br.com.coffeeandit.payments.service.StripeService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
@SecurityRequirement(name = "Bearer")

public class PagamentoController {
    public PagamentoController(final StripeService stripeService) {
        this.stripeService = stripeService;
    }

    private final StripeService stripeService;

    @Operation(summary = "Cria uma requisição de pagamento na Stripe.com")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento Criado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagamentoStripe.class)) }),
            @ApiResponse(responseCode = "403", description = "Erro de Autorização sem o escope de " + SecurityConfiguration.SCOPE_COFFEE_AND_IT_ROLE,
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Erro de Autenticação",
                    content = @Content) })
    @PostMapping(value = "/",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoStripe> charge(@Validated @RequestBody final PagamentoDto pagamentoDto, @RequestHeader(name = "Content-Type", defaultValue = MediaType.APPLICATION_JSON_VALUE) final String contentType)
            throws StripeException {
       return ResponseEntity.ok(stripeService.charge(pagamentoDto));

    }
    @Operation(summary = "Retorna uma requisição de pagamento na Stripe.com pelo Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento retornado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PagamentoStripe.class)) }),
            @ApiResponse(responseCode = "403", description = "Erro de Autorização sem    o escope de " + SecurityConfiguration.SCOPE_COFFEE_AND_IT_ROLE,
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Erro de Autenticação",
                    content = @Content) })
    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoStripe> retrieve(@PathVariable("id") final String id, @RequestHeader(name = "Content-Type", defaultValue = MediaType.APPLICATION_JSON_VALUE) final String contentType)
            throws StripeException {
        return ResponseEntity.ok(stripeService.retrieve(id));


    }
    @Operation(summary = "Retorna todos os pagamentos na Stripe.com pelo limitados pelos últimos 100 registros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento Criado",
                    content = { @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PagamentoStripe.class)))}),
            @ApiResponse(responseCode = "403", description = "Erro de Autorização sem o escope de " + SecurityConfiguration.SCOPE_COFFEE_AND_IT_ROLE,
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Erro de Autenticação",
                    content = @Content) })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PagamentoStripe>> pagamentos(@RequestHeader(name = "Content-Type", defaultValue = MediaType.APPLICATION_JSON_VALUE) final String contentType)
            throws StripeException {
        return ResponseEntity.ok(stripeService.listPayments());


    }
}