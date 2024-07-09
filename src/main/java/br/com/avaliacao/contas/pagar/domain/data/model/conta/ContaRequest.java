package br.com.avaliacao.contas.pagar.domain.data.model.conta;

import br.com.avaliacao.contas.pagar.domain.data.serialization.LocalDateDeserializer;
import br.com.avaliacao.contas.pagar.domain.data.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContaRequest {

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String descricao;

    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataVencimento;

    public @NotNull BigDecimal getValor() {
        return valor;
    }

    public void setValor(@NotNull BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public @NotNull LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(@NotNull LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
