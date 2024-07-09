package br.com.avaliacao.contas.pagar.domain.data.model.conta;

import java.math.BigDecimal;

public class ValorPagoResponse {

    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
