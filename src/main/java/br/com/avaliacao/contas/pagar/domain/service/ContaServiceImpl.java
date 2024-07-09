package br.com.avaliacao.contas.pagar.domain.service;

import br.com.avaliacao.contas.pagar.domain.data.filters.ContaFilter;
import br.com.avaliacao.contas.pagar.domain.data.filters.ContaPage;
import br.com.avaliacao.contas.pagar.domain.data.filters.ValorPagoPeriodoFilter;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.*;
import br.com.avaliacao.contas.pagar.domain.repository.ContaRepository;
import br.com.avaliacao.contas.pagar.domain.service.interfaces.ContaService;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.SituacaoEntityEnum;
import com.opencsv.CSVReader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

@Service
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    public ContaServiceImpl(final ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public ContaResponse criar(final ContaRequest conta) {
        return contaRepository.criar(conta);
    }

    @Override
    public void criar(final MultipartFile file) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(file.getOriginalFilename()));

        List<String[]> rows = reader.readAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

        for (String[] row : rows) {
            ContaRequest contaRequest = new ContaRequest();

            contaRequest.setValor(new BigDecimal(row[0]));
            contaRequest.setDescricao(row[1]);
            contaRequest.setDataVencimento(LocalDate.parse(row[2], formatter));

            contaRepository.criar(contaRequest);
        }
    }

    @Override
    public ContaResponse atualizar(final Long id, final Conta conta) {
        return contaRepository.atualizar(id, conta);
    }

    @Override
    public ContaResponse atualizar(final Long id, final String situacao) {
        return contaRepository.atualizar(id, SituacaoEntityEnum.from(situacao));
    }

    @Override
    public Page<ContaEntity> listarContas(final ContaPage contaPage, final ContaFilter filter) {
        return contaRepository.listarContas(contaPage, filter);
    }

    @Override
    public ContaResponse obterPorId(final Long id) {
        return contaRepository.obterPorId(id);
    }

    @Override
    public ValorPagoResponse obterValorPorPeriodo(final ValorPagoPeriodoFilter filter) {
        BigDecimal valor = contaRepository.obterTotalQuitado(filter);
        ValorPagoResponse response = new ValorPagoResponse();
        response.setValor(valor);
        return response;
    }

    @Override
    public List<ContaResponse> obterAPagar() {
        return contaRepository.listarContasPendentes();
    }
}
