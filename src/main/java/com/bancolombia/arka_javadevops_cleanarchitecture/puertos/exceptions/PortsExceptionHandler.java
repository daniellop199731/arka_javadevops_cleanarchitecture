package com.bancolombia.arka_javadevops_cleanarchitecture.puertos.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bancolombia.arka_javadevops_cleanarchitecture.utils.ResponseObject;

@RestControllerAdvice
public class PortsExceptionHandler {

    public static ResponseObject rObj;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleMethodArgunmentNotValidException(MethodArgumentNotValidException ex){
        rObj = new ResponseObject();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->{
            errors.put(error.getField(), error.getDefaultMessage());
        });
        rObj.setAsNotSuccessfully(errors);
        return ResponseEntity.badRequest().body(rObj);
    }

}
