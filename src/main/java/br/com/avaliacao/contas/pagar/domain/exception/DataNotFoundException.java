package br.com.avaliacao.contas.pagar.domain.exception;


import br.com.avaliacao.contas.pagar.domain.exception.util.ExceptionCode;

public class DataNotFoundException extends RuntimeException {

  private ExceptionCode exceptionCode;

  public DataNotFoundException() {
    super();
  }

  public DataNotFoundException(final String msg) {
    super(msg);
  }

  public DataNotFoundException(final String msg, final ExceptionCode exceptionCode) {
    super(msg);
    this.exceptionCode = exceptionCode;
  }

  public ExceptionCode getExceptionCode() {
    return exceptionCode;
  }
}