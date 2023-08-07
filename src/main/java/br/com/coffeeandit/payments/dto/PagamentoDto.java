package br.com.coffeeandit.payments.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagamentoDto {

    private Cartao cartao;

    public enum Moeda {
        EUR, USD, BRL;
    }
    private String descricao;
    private long quantidade;
    private Moeda moeda = Moeda.BRL;
}