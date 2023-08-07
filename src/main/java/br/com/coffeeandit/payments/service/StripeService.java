package br.com.coffeeandit.payments.service;

import br.com.coffeeandit.payments.dto.Cartao;
import br.com.coffeeandit.payments.dto.PagamentoDto;
import br.com.coffeeandit.payments.dto.PagamentoStripe;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;
import com.stripe.param.TokenCreateParams.Card;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class StripeService {

    public static final String LIMIT = "limit";
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

    public PagamentoStripe charge(final PagamentoDto pagamentoDto)
            throws StripeException {
        var token = generateCardToken(pagamentoDto.getCartao());
        var charge =  Charge.create(
                new ChargeCreateParams.Builder()
                        .setAmount(pagamentoDto.getValor())
                        .setCurrency(PagamentoDto.Moeda.BRL.toString())
                        .setDescription(pagamentoDto.getDescricao())
                        .setSource(token)
                        .build());

        return buildReponse(token, charge);
    }
    public PagamentoStripe retrieve(final String token) throws StripeException {
        var retrieve = Charge.retrieve(token);
        return buildReponse(token, retrieve);
    }
    public List<PagamentoStripe> listPayments() throws StripeException {
        var pagamentoStripes = new ArrayList<PagamentoStripe>();
        Map<String, Object> map = new HashMap<>();
        map.put(LIMIT, 50);
        ChargeCollection chargeCollection = Charge.list(map);
        if(Objects.nonNull(chargeCollection)) {
            pagamentoStripes.addAll(chargeCollection.getData().parallelStream().map(charge -> buildReponse(null, charge))
                    .toList());
            pagamentoStripes.sort(Comparator.comparing(PagamentoStripe::getCreated));
        }
        return pagamentoStripes;
    }
    private PagamentoStripe buildReponse(final String token, final Charge charge) {

        var pagamentoStripe = new PagamentoStripe();
        pagamentoStripe.setId(charge.getId());
        pagamentoStripe.setCardToken(token);
        if (Objects.isNull(token) && charge.getSource() instanceof com.stripe.model.Card card) {
            pagamentoStripe.setCardToken(card.getId());
        }
        pagamentoStripe.setStatus(charge.getStatus());
        pagamentoStripe.setCreated(LocalDateTime.ofEpochSecond(charge.getCreated(),
                0, ZoneOffset.UTC));
        pagamentoStripe.setPaymentMethod(charge.getPaymentMethod());
        pagamentoStripe.setValor(BigDecimal.valueOf(charge.getAmount()).divide(BigDecimal.valueOf(100),
                RoundingMode.CEILING));
        pagamentoStripe.setReceiptUrl(charge.getReceiptUrl());
        return pagamentoStripe;
    }
}