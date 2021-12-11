package ru.rastorguev.springhw.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rastorguev.springhw.exception.BankBookException;
import ru.rastorguev.springhw.exception.BankBookNotFoundException;
import ru.rastorguev.springhw.exception.UserNotFoundException;
import ru.rastorguev.springhw.model.ErrorDto;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("USER_ID_NOT_FOUND", exception.getUserIdNotFound(), exception.getMessage()));
    }

    @ExceptionHandler(BankBookNotFoundException.class)
    public ResponseEntity<ErrorDto> handleBankBookNotFoundException(BankBookNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("NOT_FOUND", exception.getBankBookIdNotFound(), exception.getMessage()));
    }

    @ExceptionHandler(BankBookException.class)
    public ResponseEntity<ErrorDto> handleBankBookNotFoundException(BankBookException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("BAD_REQUEST", exception.getBankBookId(), exception.getMessage()));
    }

}