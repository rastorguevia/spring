package ru.rastorguev.springhw.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BankBookDto {

    private Integer id;
    private Integer userId;
    private String number;
    private BigDecimal amount;
    private String currency;

}
