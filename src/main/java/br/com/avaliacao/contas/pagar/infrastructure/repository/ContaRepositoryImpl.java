package br.com.avaliacao.contas.pagar.infrastructure.repository;

import br.com.avaliacao.contas.pagar.domain.data.filters.ContaFilter;
import br.com.avaliacao.contas.pagar.domain.data.filters.ContaPage;
import br.com.avaliacao.contas.pagar.domain.data.filters.ValorPagoPeriodoFilter;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.*;
import br.com.avaliacao.contas.pagar.domain.exception.DataNotFoundException;
import br.com.avaliacao.contas.pagar.domain.repository.ContaRepository;
import br.com.avaliacao.contas.pagar.infrastructure.data.mapper.ContaEntityMapper;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.SituacaoEntityEnum;
import br.com.avaliacao.contas.pagar.infrastructure.repository.persistence.CriteriaContaRepository;
import br.com.avaliacao.contas.pagar.infrastructure.repository.persistence.CrudContaRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContaRepositoryImpl implements ContaRepository {

    private final CrudContaRepository crudRepository;
    private final CriteriaContaRepository criteriaRepository;
    private final ContaEntityMapper mapper;

    public ContaRepositoryImpl(final CrudContaRepository crudRepository,
                               final CriteriaContaRepository criteriaRepository,
                               final ContaEntityMapper mapper) {
        this.crudRepository = crudRepository;
        this.criteriaRepository = criteriaRepository;
        this.mapper = mapper;
    }

    @Override
    public ContaResponse criar(final ContaRequest conta) {
        ContaEntity entity = mapper.convertToContaEntity(conta);
        entity.setSituacao(SituacaoEntityEnum.PENDENTE);
        return mapper.convertToContaResponse(crudRepository.save(entity));
    }

    @Override
    public ContaResponse atualizar(final Long id, final Conta conta) {
        ContaEntity entity = mapper.convertToContaEntity(conta);
        entity.setId(id);
        return mapper.convertToContaResponse(crudRepository.save(entity));
    }

    @Override
    public ContaResponse atualizar(final Long id, final SituacaoEntityEnum situacao) {
        ContaEntity entity = crudRepository.getById(id).orElseThrow(DataNotFoundException::new);
        entity.setSituacao(situacao);
        if (situacao == SituacaoEntityEnum.LIQUIDADO) {
            entity.setDataPagamento(LocalDate.now());
        }
        return mapper.convertToContaResponse(crudRepository.save(entity));
    }

    @Override
    public Page<ContaEntity> listarContas(final ContaPage contaPage, final ContaFilter filter) {
        return criteriaRepository.findAllWithFilters(contaPage, filter);
    }

    @Override
    public ContaResponse obterPorId(final Long id) {
        ContaEntity entity = crudRepository.getById(id).orElseThrow(DataNotFoundException::new);
        return mapper.convertToContaResponse(entity);
    }

    @Override
    public BigDecimal obterTotalQuitado(ValorPagoPeriodoFilter filter) {
        return criteriaRepository.sumWithFilters(filter);
    }

    @Override
    public List<ContaResponse> listarContasPendentes() {
        List<ContaResponse> contaResponseList = new ArrayList<>();
        List<ContaEntity> contaEntityList = criteriaRepository.listContasPendentes();

        contaEntityList.forEach(contaEntity -> {
            contaResponseList.add(mapper.convertToContaResponse(contaEntity));
        });

        return contaResponseList;
    }

}
