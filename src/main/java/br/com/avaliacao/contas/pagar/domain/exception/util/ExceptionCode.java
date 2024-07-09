package br.com.avaliacao.contas.pagar.domain.exception.util;

import org.apache.catalina.util.StringUtil;

import java.util.List;

public enum ExceptionCode {

  HEADER_NOT_RECEIVED("HEADER_NOT_RECEIVED", null),
  INVALID_PAYLOAD_FIELD("INVALID_PAYLOAD_FIELD", List.of()),
  INVALID_DATA_TYPE("INVALID_DATA_TYPE", null);

  private final String exceptionCode;

  private final List<Class> ableClasses;

  ExceptionCode(final String exceptionCode, final List<Class> ableClasses) {
    this.exceptionCode = exceptionCode;
    this.ableClasses = ableClasses;
  }

  public String getExceptionCode() {
    return exceptionCode;
  }

  public List<Class> getAbleClasses() {
    return ableClasses;
  }

  public String formatExceptionCode(final String value) {
    return value.replace("-", "_").replace("__", "_") + "_" + exceptionCode;
  }
}