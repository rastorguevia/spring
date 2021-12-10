package ru.rastorguev.springhw.service;

import ru.rastorguev.springhw.model.BankBookDto;

import java.util.List;

public interface BankBookService {

    List<BankBookDto> getAllBankBookById(Integer userId);
    BankBookDto getBankBookById(Integer bankBookId);
    BankBookDto createBankBook (BankBookDto bankBookDto);

}
