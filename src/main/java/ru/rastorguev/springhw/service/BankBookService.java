package ru.rastorguev.springhw.service;

import ru.rastorguev.springhw.model.dto.BankBookDto;

import java.util.List;

public interface BankBookService {

    List<BankBookDto> getAllBankBookByUserId(Integer userId);
    BankBookDto getBankBookById(Integer bankBookId);
    BankBookDto createBankBook (BankBookDto bankBookDto);
    BankBookDto updateBankBook(BankBookDto bbd);
    void deleteBankBook(Integer bankBookId);
    void deleteAllBankBookByUserId(Integer userId);
}
