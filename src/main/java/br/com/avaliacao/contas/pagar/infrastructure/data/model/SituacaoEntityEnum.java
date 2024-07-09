package br.com.avaliacao.contas.pagar.infrastructure.data.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum SituacaoEntityEnum {

    PENDENTE,
    LIQUIDADO;


    SituacaoEntityEnum() {
        Mapping.MAPPING.put(this.name(), this);
    }

    public static SituacaoEntityEnum from(final String name) {
        final Optional<SituacaoEntityEnum> situacao = Optional.ofNullable(Mapping.MAPPING.get(name));

        if (situacao.isEmpty()) {
            throw new IllegalArgumentException(String.format("O tipo de situação processada '%s' não é suportada.", name));
        }

        return situacao.get();
    }

    private static class Mapping {
        static Map<String, SituacaoEntityEnum> MAPPING = new HashMap<>();
    }
}
