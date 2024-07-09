package br.com.avaliacao.contas.pagar.domain.exception;

public class ServerErrorException extends RuntimeException {

  public ServerErrorException() {
    super();
  }

  public ServerErrorException(final String message) {
    super(message);
  }
}
