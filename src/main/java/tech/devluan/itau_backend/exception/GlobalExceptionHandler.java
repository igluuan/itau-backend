package tech.devluan.itau_backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TransactionValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(TransactionValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", ex.getMessage());
        response.put("erros", ex.getErrors());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
}
