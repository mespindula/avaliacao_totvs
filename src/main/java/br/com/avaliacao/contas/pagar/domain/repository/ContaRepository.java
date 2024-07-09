package br.com.avaliacao.contas.pagar.domain.repository;

import br.com.avaliacao.contas.pagar.domain.data.filters.ContaFilter;
import br.com.avaliacao.contas.pagar.domain.data.filters.ContaPage;
import br.com.avaliacao.contas.pagar.domain.data.filters.ValorPagoPeriodoFilter;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.*;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.SituacaoEntityEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ContaRepository {

    ContaResponse criar(ContaRequest conta);
    ContaResponse atualizar(Long id, Conta conta);
    ContaResponse atualizar(Long id, SituacaoEntityEnum situacao);
    Page<ContaEntity> listarContas(ContaPage contaPage, ContaFilter filter);
    ContaResponse obterPorId(Long id);
    BigDecimal obterTotalQuitado(ValorPagoPeriodoFilter filter);
    List<ContaResponse> listarContasPendentes();
}
