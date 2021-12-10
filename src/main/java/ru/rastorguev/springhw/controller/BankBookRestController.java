package ru.rastorguev.springhw.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rastorguev.springhw.exception.UserNotFoundException;
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

    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<List<BankBookDto>> getAllBankBooksByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(bankBookService.getAllBankBookById(userId));
    }

    @GetMapping("/{bankBookId}")
    public ResponseEntity<BankBookDto> getBankBookById(@PathVariable Integer bankBookId) {
        return ResponseEntity.ok(bankBookService.getBankBookById(bankBookId));
    }

    @PostMapping
    public ResponseEntity<BankBookDto> createBankBook(@RequestBody BankBookDto bbd) {
        return ResponseEntity.ok(bankBookService.createBankBook(bbd));
    }

}
