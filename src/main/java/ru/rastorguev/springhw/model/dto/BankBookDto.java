package ru.rastorguev.springhw.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.rastorguev.springhw.validation.Currency;
import ru.rastorguev.springhw.validation.marker.Create;
import ru.rastorguev.springhw.validation.marker.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
public class BankBookDto {

    @Null(groups = Create.class, message = "При создании id должен отсутствовать!")
    @NotNull(groups = Update.class, message = "При обновлении id не должен отсутствовать!")
    private Integer id;

    @NotNull
    private Integer userId;

    @NotBlank(message = "Number не должен быть пустым или отсутствовать!")
    private String number;

    @PositiveOrZero(message = "Баланс счета должен быть больше, либо равен 0!")
    @NotNull
    private BigDecimal amount;

    @Currency
    @NotNull
    private String currency;

}
