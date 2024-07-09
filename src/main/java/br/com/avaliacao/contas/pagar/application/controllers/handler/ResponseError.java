package br.com.avaliacao.contas.pagar.application.controllers.handler;

import br.com.avaliacao.contas.pagar.domain.data.model.ModelBusiness;

public class ResponseError extends ModelBusiness {

  private String code;

  private String message;

  public ResponseError(final String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
