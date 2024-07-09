package br.com.avaliacao.contas.pagar.domain.data.model.conta;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Situacao {

    PENDENTE,
    LIQUIDADO;


    Situacao() {
        Mapping.MAPPING.put(this.name(), this);
    }

    public static Situacao from(final String name) {
        final Optional<Situacao> situacao = Optional.ofNullable(Mapping.MAPPING.get(name));

        if (situacao.isEmpty()) {
            throw new IllegalArgumentException(String.format("O tipo de situação processada '%s' não é suportada.", name));
        }

        return situacao.get();
    }

    private static class Mapping {
        static Map<String, Situacao> MAPPING = new HashMap<>();
    }
}
