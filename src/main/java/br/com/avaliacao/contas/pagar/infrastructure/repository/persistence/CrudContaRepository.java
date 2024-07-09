package br.com.avaliacao.contas.pagar.infrastructure.repository.persistence;

import br.com.avaliacao.contas.pagar.infrastructure.data.model.ContaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudContaRepository extends CrudRepository<ContaEntity, Long> {

    Optional<ContaEntity> getById(Long id);
}
