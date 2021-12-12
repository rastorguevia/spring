package ru.rastorguev.springhw.model.exception;

public class UserNotFoundException extends RuntimeException {

    private Integer userIdNotFound;

    public UserNotFoundException(String message, Integer userIdNotFound) {
        super(message + "ID: " + userIdNotFound);
        this.userIdNotFound = userIdNotFound;
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public Integer getUserIdNotFound() {
        return userIdNotFound;
    }
}
