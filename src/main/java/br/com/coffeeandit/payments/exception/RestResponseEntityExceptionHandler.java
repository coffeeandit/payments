package br.com.coffeeandit.payments.exception;

import br.com.coffeeandit.payments.dto.ErroPagamento;
import com.stripe.exception.CardException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { CardException.class })
    protected ResponseEntity<Object> handleConflict(
            final CardException cardException, final WebRequest request) {
        var error = new ErroPagamento();
        error.setMessage(cardException.getMessage());
        return handleExceptionInternal(cardException, error,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}