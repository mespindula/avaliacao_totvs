package br.com.avaliacao.contas.pagar.domain.data.filters;

import br.com.avaliacao.contas.pagar.domain.data.model.ModelBusiness;
import br.com.avaliacao.contas.pagar.domain.data.serialization.LocalDateDeserializer;
import br.com.avaliacao.contas.pagar.domain.data.serialization.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

public class ContaFilter extends ModelBusiness {

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataVencimento;

    private String descricao;

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
