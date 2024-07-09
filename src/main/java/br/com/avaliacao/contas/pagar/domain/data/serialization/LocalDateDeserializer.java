package br.com.avaliacao.contas.pagar.domain.data.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

  protected LocalDateDeserializer() {
    super(LocalDate.class);
  }

  @Override
  public LocalDate deserialize(
      final JsonParser jsonParser,
      final DeserializationContext deserializationContext)
      throws IOException {
    return LocalDate.parse(jsonParser.readValueAs(String.class));
  }
}