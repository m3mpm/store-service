package org.m3mpm.storeservice.advice;

import org.m3mpm.storeservice.exception.EntityNotFoundException;
import org.m3mpm.storeservice.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(EntityNotFoundException ex) {

        String message = (ex.getMessage() != null) ? ex.getMessage() : "Exception: Address not found";

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex){

        // 1. Берем список всех ошибок из BindingResult
        // 2. Склеиваем их в одну строку через Stream API
        String message = ex.getBindingResult()
                .getFieldErrors() // Получаем List<FieldError>
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage()) // Превращаем в строку "поле: сообщение"
                .collect(Collectors.joining("; ")); // Склеиваем через "; "

        // 3. Если вдруг список пуст (редко, но бывает), подстрахуемся
        if (message.isEmpty()) {
            message = "Exception: Validation failed";
        }

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
