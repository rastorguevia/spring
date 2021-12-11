package ru.rastorguev.springhw.service;

import org.springframework.stereotype.Component;
import ru.rastorguev.springhw.exception.BankBookException;
import ru.rastorguev.springhw.exception.BankBookNotFoundException;
import ru.rastorguev.springhw.exception.UserNotFoundException;
import ru.rastorguev.springhw.model.BankBookDto;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BankBookServiceImpl implements BankBookService {

    private final Map<Integer, BankBookDto> bankBookMap = new ConcurrentHashMap<>();
    private final AtomicInteger sequenceId = new AtomicInteger(1);

    @PostConstruct
    private void init() {
        bankBookMap.put(1, new BankBookDto(sequenceId.getAndIncrement(), 1, "12345", new BigDecimal(250000), "RUB"));
        bankBookMap.put(2, new BankBookDto(sequenceId.getAndIncrement(), 1, "123456", new BigDecimal(30000), "EUR"));
    }

    @Override
    public List<BankBookDto> getAllBankBookByUserId(Integer userId) {
        List<BankBookDto> list =
                bankBookMap.values()
                .stream()
                .filter(bankBookDto -> bankBookDto.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (list.isEmpty()) throw new UserNotFoundException("Счета по id пользователя не найдены! ", userId);
        else return list;
    }

    @Override
    public BankBookDto getBankBookById(Integer bankBookId) {
        BankBookDto bbd = bankBookMap.values().stream()
                .filter(bankBookDto -> bankBookDto.getId().equals(bankBookId))
                .findFirst()
                .orElse(null);

        if (bbd == null) throw new BankBookNotFoundException("Такой счет не найден! ", bankBookId);
        else return bbd;
    }

    @Override
    public BankBookDto createBankBook(BankBookDto bankBookDto) {

        boolean bankBookExist = bankBookMap.values()
                .stream()
                .anyMatch(bbd -> bbd.getUserId().equals(bankBookDto.getUserId())
                        && bbd.getNumber().equals(bankBookDto.getNumber())
                        && bbd.getCurrency().equals(bankBookDto.getCurrency()));

        if (bankBookExist) throw new BankBookException(
                "Cчет с данной валютой уже имеется в хранилище: BankBookDto.id = " + bankBookDto.getId() + " ",
                bankBookDto.getId());

        bankBookDto.setId(sequenceId.getAndIncrement());
        bankBookMap.put(bankBookDto.getId(), bankBookDto);
        return bankBookDto;
    }



}
