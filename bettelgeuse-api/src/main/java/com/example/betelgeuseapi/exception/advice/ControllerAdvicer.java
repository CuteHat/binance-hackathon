package com.example.betelgeuseapi.exception.advice;

import com.example.betelgeuseapi.exception.HandledException;
import com.example.betelgeuseapi.exception.detail.HandledExceptionDetail;
import com.example.betelgeuseapi.exception.detail.ValidationDetail;
import com.example.betelgeuseapi.exception.model.FieldValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@ControllerAdvice
public class ControllerAdvicer {

    @ExceptionHandler(HandledException.class)
    public ResponseEntity<HandledExceptionDetail> handleHandledException(HandledException exception) {
        log.error(exception.toString());
        return new ResponseEntity<>(exception.getDetail(), exception.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Constraint violation exception encountered: {}", ex.toString());
        return new ValidationDetail(buildValidationErrors(ex.getBindingResult()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ValidationDetail handleConstraintViolation(ConstraintViolationException ex) {
        log.error("Constraint violation exception encountered: {}", ex.toString());
        return new ValidationDetail(buildValidationErrors(ex.getConstraintViolations()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseBody
    public ValidationDetail handleConstraintViolation(MissingServletRequestPartException ex) {
        log.error("Servlet request part exception encountered: {}", ex.toString());
        return new ValidationDetail(List.of(new FieldValidationError(ex.getRequestPartName(), ex.getMessage())));
    }

    private List<FieldValidationError> buildValidationErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().map((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            return new FieldValidationError(fieldName, errorMessage);
        }).collect(Collectors.toList());
    }

    private List<FieldValidationError> buildValidationErrors(Set<ConstraintViolation<?>> violations) {
        return violations.stream().map(violation -> {
            String field = StreamSupport.stream(violation.getPropertyPath().spliterator(), false).reduce((first, second) -> second).orElse(null).toString();

            return new FieldValidationError(field, violation.getMessage());
        }).collect(Collectors.toList());
    }

}
