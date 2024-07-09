package br.com.avaliacao.contas.pagar.infrastructure.repository.persistence;

import br.com.avaliacao.contas.pagar.domain.data.filters.ContaFilter;
import br.com.avaliacao.contas.pagar.domain.data.filters.ContaPage;
import br.com.avaliacao.contas.pagar.domain.data.filters.ValorPagoPeriodoFilter;
import br.com.avaliacao.contas.pagar.domain.data.model.conta.Situacao;
import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CriteriaContaRepository {

    private final EntityManager em;
    private final CriteriaBuilder cb;

    public CriteriaContaRepository(final EntityManager em) {
        this.em = em;
        this.cb = em.getCriteriaBuilder();
    }

    public Page<ContaEntity> findAllWithFilters(final ContaPage contaPage, final ContaFilter filter) {
        CriteriaQuery<ContaEntity> entityCriteriaQuery = cb.createQuery(ContaEntity.class);
        Root<ContaEntity> root = entityCriteriaQuery.from(ContaEntity.class);
        Predicate predicate = getPendenteFilterPrecicate(filter, root);
        entityCriteriaQuery.where(predicate);
        setOrder(contaPage, entityCriteriaQuery, root);

        TypedQuery<ContaEntity> typedQuery = em.createQuery(entityCriteriaQuery);
        typedQuery.setFirstResult(contaPage.getPageNumber() * contaPage.getPageSize());
        typedQuery.setMaxResults(contaPage.getPageSize());

        Pageable pageable = getPegeable(contaPage);

        long count = getContasCount(filter);

        return new PageImpl<>(typedQuery.getResultList(), pageable, count);
    }

    private Predicate getPendenteFilterPrecicate(final ContaFilter filter, final Root<ContaEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(filter.getDataVencimento())) {
            predicates.add(
                    cb.equal(root.get("dataVencimento"), filter.getDataVencimento())
            );
        }

        if(Objects.nonNull(filter.getDescricao())) {
            predicates.add(
                    cb.like(root.get("descricao"), "%" + filter.getDescricao() + "%")
            );
        }

        predicates.add(
                cb.like(root.get("situacao"), Situacao.PENDENTE.name())
        );

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(final ContaPage contaPage,
                          final CriteriaQuery<ContaEntity> criteriaQuery,
                          final Root<ContaEntity> root) {

        if(contaPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(cb.asc(root.get(contaPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(cb.desc(root.get(contaPage.getSortBy())));
        }
    }

    private Pageable getPegeable(final ContaPage contaPage) {
        Sort sort = Sort.by(contaPage.getSortDirection(), contaPage.getSortBy());
        return PageRequest.of(contaPage.getPageNumber(), contaPage.getPageSize(), sort);
    }

    private long getContasCount(final Predicate predicate) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ContaEntity> countRoot = countQuery.from(ContaEntity.class);
        countQuery.select(cb.count(countRoot)).where(predicate);
        return em.createQuery(countQuery).getSingleResult();
    }

    private long getContasCount(final ContaFilter filter) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ContaEntity> countRoot = countQuery.from(ContaEntity.class);
        Predicate predicate = getPendenteFilterPrecicate(filter, countRoot);
        countQuery.select(cb.count(countRoot)).where(predicate);
        return em.createQuery(countQuery).getSingleResult();
    }

    public BigDecimal sumWithFilters(final ValorPagoPeriodoFilter filter) {
        CriteriaQuery<BigDecimal> entityCriteriaQuery = cb.createQuery(BigDecimal.class);
        Root<ContaEntity> root = entityCriteriaQuery.from(ContaEntity.class);

        Predicate predicate = getLiquidadoPeriodoPredicate(filter, root);
        entityCriteriaQuery.where(predicate);

        entityCriteriaQuery.select(cb.sum(root.get("valor")));

        return em.createQuery(entityCriteriaQuery).getSingleResult();
    }

    private Predicate getLiquidadoPeriodoPredicate(final ValorPagoPeriodoFilter filter, final Root<ContaEntity> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(filter.getDataPagInicial()) && Objects.nonNull(filter.getDataPagFinal())) {
            Predicate date = cb.between(root.get("dataPagamento"), filter.getDataPagInicial(), filter.getDataPagFinal());
            predicates.add(date);
        } else if(Objects.nonNull(filter.getDataPagInicial())) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("dataPagamento"), filter.getDataPagInicial())
            );
        } else if(Objects.nonNull(filter.getDataPagFinal())) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("dataPagamento"), filter.getDataPagFinal())
            );
        }

        predicates.add(
                cb.like(root.get("situacao"), Situacao.LIQUIDADO.name())
        );

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    public List<ContaEntity> listContasPendentes() {
        CriteriaQuery<ContaEntity> entityCriteriaQuery = cb.createQuery(ContaEntity.class);
        Root<ContaEntity> root = entityCriteriaQuery.from(ContaEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(
                cb.like(root.get("situacao"), Situacao.PENDENTE.name())
        );
        Predicate predicate = cb.and(predicates.toArray(new Predicate[0]));

        entityCriteriaQuery.where(predicate);

        return em.createQuery(entityCriteriaQuery).getResultList();
    }
}
