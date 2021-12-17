package ru.rastorguev.springhw.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "bank_book", schema = "cl")
public class BankBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    //@NotNull
    @Column(nullable = false)
    private String number;

    //@NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    //@NotNull
    @Column(nullable = false)
    private String currency;

}
