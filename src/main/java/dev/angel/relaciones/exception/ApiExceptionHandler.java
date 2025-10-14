package dev.angel.relaciones.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(basePackages = "dev.angel.relaciones")
public class ApiExceptionHandler {

    // Choques de BD (carreras)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,Object> unique() {
        return Map.of("detail","Ya existe un DNI con ese número");
    }

    // Validación @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> validation(MethodArgumentNotValidException ex) {
        var fe = ex.getBindingResult().getFieldError();
        return Map.of("detail", fe != null ? fe.getField()+": "+fe.getDefaultMessage() : "Datos inválidos");
    }
}
