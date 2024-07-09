package br.com.avaliacao.contas.pagar.application.controllers;

import br.com.avaliacao.contas.pagar.domain.data.filters.ContaFilter;
import br.com.avaliacao.contas.pagar.domain.data.filters.ContaPage;
import br.com.avaliacao.contas.pagar.domain.data.filters.ValorPagoPeriodoFilter;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.Conta;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaRequest;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ContaResponse;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.ValorPagoResponse;
import br.com.avaliacao.contas.pagar.domain.service.ContaServiceImpl;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/contas-pagar/v1/conta")
public class ContaController implements ContaApi {

    private final ContaServiceImpl contaService;

    public ContaController(final ContaServiceImpl contaService) {
        this.contaService = contaService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaResponse create(@Valid @RequestBody final ContaRequest conta) {
        return contaService.criar(conta);
    }

    @Override
    @PutMapping("/{id}")
    public ContaResponse update(@PathVariable final Long id, @Valid @RequestBody final Conta conta) {
        return contaService.atualizar(id, conta);
    }

    @Override
    @PatchMapping("/{id}/{situacao}")
    public ContaResponse update(@PathVariable final Long id, @PathVariable final String situacao) {
        return contaService.atualizar(id, situacao);
    }

    @Override
    @GetMapping("/listar-pendentes")
    public ResponseEntity<Page<ContaEntity>> list(final ContaPage contaPage, final ContaFilter filter) {
        return new ResponseEntity<>(contaService.listarContas(contaPage, filter),
                HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ContaResponse get(@PathVariable final Long id) {
        return contaService.obterPorId(id);
    }

    @Override
    @GetMapping("/soma-liquidados")
    public ValorPagoResponse getValorTotalPago(final ValorPagoPeriodoFilter filter) {
        return contaService.obterValorPorPeriodo(filter);
    }

    @GetMapping("/export-pendentes")
    public void exportCSV(final HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        String fileName = "conta-data.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+fileName);

        StatefulBeanToCsv<ContaResponse> writer = new StatefulBeanToCsvBuilder<ContaResponse>(response.getWriter())
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        writer.write(contaService.obterAPagar());
    }

    @PostMapping("/import-pendentes")
    public ResponseEntity<String> importCSV(final @RequestParam MultipartFile file) throws IOException {
        contaService.criar(file);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }
}
