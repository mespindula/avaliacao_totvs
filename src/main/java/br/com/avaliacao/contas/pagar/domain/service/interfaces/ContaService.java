package br.com.avaliacao.contas.pagar.domain.service.interfaces;

import br.com.avaliacao.contas.pagar.domain.data.filters.ContaFilter;
import br.com.avaliacao.contas.pagar.domain.data.filters.ContaPage;
import br.com.avaliacao.contas.pagar.domain.data.filters.ValorPagoPeriodoFilter;

import br.com.avaliacao.contas.pagar.domain.data.model.conta.Conta;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaRequest;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaResponse;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ValorPagoResponse;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;


public interface ContaService {

    ContaResponse criar(ContaRequest conta);
    void criar(final MultipartFile file) throws Exception;
    ContaResponse atualizar(Long id, Conta conta);
    ContaResponse atualizar(Long id, String situacao);
    Page<ContaEntity> listarContas(ContaPage contaPage, ContaFilter filter);
    ContaResponse obterPorId(Long id);
    ValorPagoResponse obterValorPorPeriodo(ValorPagoPeriodoFilter filter);
    List<ContaResponse> obterAPagar();
}
