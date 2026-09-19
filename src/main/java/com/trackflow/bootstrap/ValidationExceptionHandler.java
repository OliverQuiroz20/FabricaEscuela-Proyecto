package com.trackflow.bootstrap;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail camposInvalidos(MethodArgumentNotValidException e) {
        Map<String, String> campos = new LinkedHashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            campos.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problema.setTitle("Datos obligatorios incompletos");
        problema.setDetail("La solicitud no se puede procesar porque faltan datos obligatorios");
        problema.setProperty("camposFaltantes", campos);

        return problema;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ProblemDetail cuerpoIlegible(HttpMessageNotReadableException e) {
        String causa = e.getMostSpecificCause().getMessage();

        ProblemDetail problema = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problema.setTitle("Valor no admitido");
        problema.setDetail("Alguno de los campos trae un valor que no se puede interpretar");
        problema.setProperty("causa", causa);

        return problema;
    }
}
