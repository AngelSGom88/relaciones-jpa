package dev.angel.relaciones._1_1_bidirec.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    // Unicidad/constraints de la BD que se escapen
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleUnique() {
        return Map.of("detail", "Ya existe un DNI con ese número");
    }

    // Validaciones @Valid (opcional)
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        var fe = ex.getBindingResult().getFieldError();
        String msg = fe != null ? fe.getField() + ": " + fe.getDefaultMessage() : "Datos inválidos";
        return Map.of("detail", msg);
    }


    // temporal para ver si entra el advice pase lo que pase
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String,Object> catchAll(Exception ex) {
//        System.out.println(">> ApiExceptionHandler.catchAll: " + ex.getClass().getSimpleName());
//        return Map.of("message", ex.getClass().getSimpleName());
//    }


}