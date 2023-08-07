package br.com.coffeeandit.payments.service;

import br.com.coffeeandit.payments.dto.Cartao;
import br.com.coffeeandit.payments.dto.PagamentoDto;
import br.com.coffeeandit.payments.dto.RespostaPagamento;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.param.TokenCreateParams.Card;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;



    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
    private String generateCardToken(final Cartao cartao) throws StripeException {
        var card = new Card.Builder().setNumber(cartao.getNumero())
                .setExpMonth(String.valueOf(cartao.getMesExpiracao()))
                .setExpYear(String.valueOf(cartao.getAnoExpiracao()))
                .setCvc(cartao.getCvc()).build();

                var params = TokenCreateParams.builder().setCard(card).build();

        var token = Token.create(params);
        return token.getId();
    }
    public RespostaPagamento charge(final PagamentoDto pagamentoDto)
            throws StripeException {
        var token = generateCardToken(pagamentoDto.getCartao());
        var response =   Charge.create(
                new ChargeCreateParams.Builder()
                        .setAmount(pagamentoDto.getQuantidade())
                        .setCurrency(PagamentoDto.Moeda.BRL.toString())
                        .setDescription(pagamentoDto.getDescricao())
                        .setSource(token)
                        .build());
        var respostaPagamento = new RespostaPagamento();
        respostaPagamento.setId(response.getId());
        respostaPagamento.setTimestamp(response.getCreated());
        respostaPagamento.setStatus(response.getStatus());
        respostaPagamento.setPaymentMethod(response.getPaymentMethod());
        return respostaPagamento;
    }
}