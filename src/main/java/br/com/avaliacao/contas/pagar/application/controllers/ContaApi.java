package br.com.avaliacao.contas.pagar.application.controllers;

import br.com.avaliacao.contas.pagar.domain.data.filters.ContaFilter;
import br.com.avaliacao.contas.pagar.domain.data.filters.ContaPage;
import br.com.avaliacao.contas.pagar.domain.data.filters.ValorPagoPeriodoFilter;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.Conta;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaRequest;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaResponse;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ValorPagoResponse;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "API Rest para gerenciamento de contas")
public interface ContaApi {

    @Operation(description = "Cadastrar conta")
    ContaResponse create(ContaRequest conta);

    @Operation(description = "Atualizar conta")
    ContaResponse update(Long id, Conta conta);

    @Operation(description = "Alterar a situação da conta")
    ContaResponse update(Long id, String situacao);

    @Operation(description = "Obter a lista de contas a pagar, com filtro de data de vencimento e descrição")
    ResponseEntity<Page<ContaEntity>> list(ContaPage contaPage, ContaFilter filter);

    @Operation(description = "Obter conta filtrando o id")
    ContaResponse get(Long id);

    @Operation(description = "Obter valor total pago por período")
    ValorPagoResponse getValorTotalPago(ValorPagoPeriodoFilter filter);

    @Operation(description = "Exportação de contas a pagar via arquivo csv")
    void exportCSV(HttpServletResponse response) throws Exception;

    @Operation(description = "Importação de contas a pagar via arquivo csv")
    ResponseEntity<String> importCSV(final @RequestParam MultipartFile file) throws Exception;
}
