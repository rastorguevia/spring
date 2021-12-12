package ru.rastorguev.springhw.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rastorguev.springhw.model.exception.BankBookException;
import ru.rastorguev.springhw.model.exception.BankBookNotFoundException;
import ru.rastorguev.springhw.model.exception.UserNotFoundException;
import ru.rastorguev.springhw.model.dto.ErrorDto;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    public ErrorDto handleMissingPathVariableException(MissingPathVariableException exception) {
        return new ErrorDto(HttpStatus.BAD_REQUEST.name(), exception.getMessage());
    }

}
