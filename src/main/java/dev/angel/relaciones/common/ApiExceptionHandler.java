package dev.angel.relaciones.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public Map<String, Object> handleUniqueViolation(Exception ex) {
        return Map.of(
                "error", "DNI duplicado",
                "message", "Ya existe un DNI con ese número"
        );
    }

    // Opcional: peticiones inválidas (validaciones @Valid)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        var field = ex.getBindingResult().getFieldError();
        String msg = (field != null) ? field.getField() + ": " + field.getDefaultMessage() : "Datos inválidos";
        return Map.of("error", "Validación", "message", msg);
    }

    // Opcional: para usar ResponseStatusException en servicios/controladores
    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleRse(org.springframework.web.server.ResponseStatusException ex) {
        return Map.of("error", ex.getStatusCode().toString(), "message", ex.getReason());
    }
}
