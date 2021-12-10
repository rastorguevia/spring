package ru.rastorguev.springhw.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String status;
    private Integer userIdNotFound;
    private String message;

}
