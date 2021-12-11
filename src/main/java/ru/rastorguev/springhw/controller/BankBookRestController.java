package ru.rastorguev.springhw.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rastorguev.springhw.model.BankBookDto;
import ru.rastorguev.springhw.service.BankBookService;

import java.util.List;

@RestController
@RequestMapping("/bank-book")
public class BankBookRestController {

    BankBookService bankBookService;

    public BankBookRestController(BankBookService bankBookService) {
        this.bankBookService = bankBookService;
    }

    @GetMapping({"/by-user-id/{userId}", "/"})
    public ResponseEntity<List<BankBookDto>> getAllBankBooksByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(bankBookService.getAllBankBookByUserId(userId));
    }

    @GetMapping({"/{bankBookId}", "/"})
    public ResponseEntity<BankBookDto> getBankBookById(@PathVariable Integer bankBookId) {
        return ResponseEntity.ok(bankBookService.getBankBookById(bankBookId));
    }

    @PostMapping
    public ResponseEntity<BankBookDto> createBankBook(@RequestBody BankBookDto bbd) {
        return ResponseEntity.ok(bankBookService.createBankBook(bbd));
    }

    @PutMapping
    public ResponseEntity<BankBookDto> updateBankBook(@RequestBody BankBookDto bbd) {
        return ResponseEntity.ok(bankBookService.updateBankBook(bbd));
    }

    @DeleteMapping({"/{bankBookId}", "/"})
    public void deleteBankBook(@PathVariable Integer bankBookId) {
        bankBookService.deleteBankBook(bankBookId);
    }

    @DeleteMapping("/by-user-id/{userId}")
    public void deleteAllBankBookByUserId(@PathVariable Integer userId) {
        bankBookService.deleteAllBankBookByUserId(userId);
    }

}
