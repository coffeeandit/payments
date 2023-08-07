package br.com.coffeeandit.payments.controller;

import br.com.coffeeandit.payments.dto.PagamentoDto;
import br.com.coffeeandit.payments.dto.RespostaPagamento;
import br.com.coffeeandit.payments.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PagamentoController {

    public PagamentoController(final StripeService stripeService) {
        this.stripeService = stripeService;
    }

    private final StripeService stripeService;

    @PostMapping(value = "/pagamento", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespostaPagamento> charge(@Validated @RequestBody final PagamentoDto pagamentoDto)
            throws StripeException {
       return ResponseEntity.ok(stripeService.charge(pagamentoDto));

    }

}