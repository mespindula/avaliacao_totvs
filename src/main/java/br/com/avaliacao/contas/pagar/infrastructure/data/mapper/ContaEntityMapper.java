package br.com.avaliacao.contas.pagar.infrastructure.data.mapper;

import br.com.avaliacao.contas.pagar.domain.data.model.conta.Conta;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaRequest;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaResponse;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContaEntityMapper {

    ContaEntity convertToContaEntity(ContaRequest conta);
    ContaResponse convertToContaResponse(ContaEntity entity);
    ContaEntity convertToContaEntity(Conta conta);

}
