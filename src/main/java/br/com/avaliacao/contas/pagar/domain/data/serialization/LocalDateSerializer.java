package br.com.avaliacao.contas.pagar.domain.data.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

  public LocalDateSerializer() {
    super(LocalDate.class);
  }

  @Override
  public void serialize(
      final LocalDate localDate,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
  }
}