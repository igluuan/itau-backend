package tech.devluan.itau_backend.exception;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TransactionValidationException extends RuntimeException {
    private final List<String> errors;

    public TransactionValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
