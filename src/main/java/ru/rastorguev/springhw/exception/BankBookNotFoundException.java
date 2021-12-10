package ru.rastorguev.springhw.exception;

public class BankBookNotFoundException extends RuntimeException {

    private Integer bankBookIdNotFound;

    public BankBookNotFoundException(String message, Integer bankBookIdNotFound) {
        super(message + "ID: " + bankBookIdNotFound);
        this.bankBookIdNotFound = bankBookIdNotFound;
    }

    public Integer getBankBookIdNotFound() {
        return bankBookIdNotFound;
    }
}
