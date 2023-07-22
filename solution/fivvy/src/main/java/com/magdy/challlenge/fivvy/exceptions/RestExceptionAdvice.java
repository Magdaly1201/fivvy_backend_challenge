package com.magdy.challlenge.fivvy.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        if (ex.getBindingResult().hasErrors()) {
            List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(err -> {
                return "The field : '" + err.getField() + "' " + err.getDefaultMessage();
            }).collect(Collectors.toList());
            response.put("errors", errors);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }
}