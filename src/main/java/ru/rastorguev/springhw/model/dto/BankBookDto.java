package ru.rastorguev.springhw.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class BankBookDto {

    private Integer id;
    private Integer userId;
    private String number;
    private BigDecimal amount;
    private String currency;

}
