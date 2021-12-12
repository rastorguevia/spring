package ru.rastorguev.springhw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String status;
    private Integer errorId;
    private String message;

    public ErrorDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}