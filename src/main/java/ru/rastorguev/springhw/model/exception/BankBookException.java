package ru.rastorguev.springhw.model.exception;

public class BankBookException extends RuntimeException {

    private final Integer bankBookId;

    public BankBookException(String message, Integer bankBookId) {
        super(message + "ID: " + bankBookId);
        this.bankBookId = bankBookId;
    }

    public Integer getBankBookId() {
        return bankBookId;
    }
}
