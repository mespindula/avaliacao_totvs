package br.com.avaliacao.contas.pagar.domain.data.filters;

import br.com.avaliacao.contas.pagar.domain.data.serialization.LocalDateDeserializer;
import br.com.avaliacao.contas.pagar.domain.data.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

public class ValorPagoPeriodoFilter {

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataPagInicial;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataPagFinal;

    public LocalDate getDataPagInicial() {
        return dataPagInicial;
    }

    public void setDataPagInicial(LocalDate dataPagInicial) {
        this.dataPagInicial = dataPagInicial;
    }

    public LocalDate getDataPagFinal() {
        return dataPagFinal;
    }

    public void setDataPagFinal(LocalDate dataPagFinal) {
        this.dataPagFinal = dataPagFinal;
    }
}
