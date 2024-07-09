package br.com.avaliacao.contas.pagar.application.controllers.handler;

import br.com.avaliacao.contas.pagar.domain.exception.DataNotFoundException;
import br.com.avaliacao.contas.pagar.domain.exception.ServerErrorException;
import br.com.avaliacao.contas.pagar.domain.exception.UnauthorizedException;
import br.com.avaliacao.contas.pagar.domain.exception.util.ExceptionCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.ResponseEntity.*;

@ControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseMessages> handleUnauthorizedException(final UnauthorizedException exception) {
        return status(HttpStatus.UNAUTHORIZED)
                .body(nonNull(exception.getMessage()) ? buildMessages(exception.getMessage(), "") : null);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ResponseMessages> handleServerErrorException(final ServerErrorException exception) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseMessages> handleDataNotFoundException(final DataNotFoundException exception) {
        if (isNull(exception.getMessage())) {
            return notFound().build();
        }

        return status(HttpStatus.NOT_FOUND)
                .body(buildMessages(exception.getMessage(), exception.getExceptionCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessages> handleConstraintViolationException(final ConstraintViolationException exception) {
        final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        final Set<String> messages = new HashSet<>();
        final List<ResponseError> responseErrors = new ArrayList<>();
        constraintViolations.forEach(c -> {
            final String message = format("{0} {1}", c.getPropertyPath(), c.getMessage());
            messages.add(message);
            if (ExceptionCode.INVALID_PAYLOAD_FIELD.getAbleClasses().contains(c.getRootBeanClass())) {
                final String exceptionCode = ExceptionCode.INVALID_PAYLOAD_FIELD.formatExceptionCode(c.getPropertyPath().toString());
                responseErrors.add(new ResponseError(exceptionCode, message));
            }
        });
        return badRequest().body(new ResponseMessages(messages, responseErrors.isEmpty() ? null : responseErrors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseMessages> handleTypeMismatch(final BindException exception) {
        if (nonNull(exception) && nonNull(exception.getFieldError()) && isNotBlank(exception.getFieldError().getField())) {
            final String message = format("{0} invalid data type", exception.getFieldError().getField());
            final String exceptionCode = ExceptionCode.INVALID_PAYLOAD_FIELD.formatExceptionCode(exception.getFieldError().getField());
            return badRequest().body(buildMessages(message, exceptionCode));
        }
        return badRequest().body(buildMessages("invalid data type in the request", ExceptionCode.INVALID_DATA_TYPE));
    }

    private ResponseMessages buildMessages(final String message, final ExceptionCode exceptionCode) {
        return buildMessages(message, nonNull(exceptionCode) ? exceptionCode.getExceptionCode() : null);
    }

    private ResponseMessages buildMessages(final String message, final String exceptionCode) {
        final ResponseMessages responseMessages = new ResponseMessages();
        responseMessages.addMessage(message);
        if (isNotBlank(exceptionCode)) {
            responseMessages.addError(exceptionCode, message);
        }
        return responseMessages;
    }
}
