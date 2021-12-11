package ru.rastorguev.springhw.service;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.rastorguev.springhw.exception.BankBookException;
import ru.rastorguev.springhw.exception.BankBookNotFoundException;
import ru.rastorguev.springhw.exception.UserNotFoundException;
import ru.rastorguev.springhw.model.BankBookDto;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class BankBookServiceImpl implements BankBookService {

    private final Map<Integer, BankBookDto> bankBookMap = new ConcurrentHashMap<>();
    private final AtomicInteger sequenceId = new AtomicInteger(1);

    @PostConstruct
    private void init() {
        //bankBookMap.put(1, new BankBookDto(sequenceId.getAndIncrement(), 1, "12345", new BigDecimal(250000), "RUB"));
        //bankBookMap.put(2, new BankBookDto(sequenceId.getAndIncrement(), 1, "123456", new BigDecimal(30000), "EUR"));
        bankBookMap.put(1, BankBookDto.builder()
                .id(sequenceId.getAndIncrement())
                .userId(1)
                .number("12345")
                .amount(new BigDecimal(250000))
                .currency("RUB")
                .build()
        );

        bankBookMap.put(2, BankBookDto.builder()
                .id(sequenceId.getAndIncrement())
                .userId(1)
                .number("123456")
                .amount(new BigDecimal(30000))
                .currency("EUR")
                .build()
        );
    }

    @Override
    public List<BankBookDto> getAllBankBookByUserId(Integer userId) {
        List<BankBookDto> list =
                bankBookMap.values()
                .stream()
                .filter(bankBookDto -> bankBookDto.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list)) throw new UserNotFoundException("Счета по id пользователя не найдены! ", userId);
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

    @Override
    public BankBookDto updateBankBook(BankBookDto bbd) {
        BankBookDto bankBookDtoFromMap = bankBookMap.get(bbd.getId());
        if (bankBookDtoFromMap == null)
            throw new BankBookException("Такого счета не существует. В обновлении отказано. ", bbd.getId());

        if (!bankBookDtoFromMap.getNumber().equalsIgnoreCase(bbd.getNumber()))
            throw new BankBookException("Номер счета менять нельзя. В обновлении отказано. ", bbd.getId());

        bankBookMap.put(bbd.getId(), bbd);
        return bbd;
    }

    @Override
    public void deleteBankBook(Integer bankBookId) {
        BankBookDto bankBookDtoFromMap = bankBookMap.get(bankBookId);
        if (bankBookDtoFromMap == null)
            throw new BankBookNotFoundException("Такого счета не существует. В удалении отказано. ", bankBookId);

        bankBookMap.remove(bankBookId);
    }

    @Override
    public void deleteAllBankBookByUserId(Integer userId) {
        List<BankBookDto> userBankBooks = bankBookMap.values()
                .stream()
                .filter(bankBookDto -> bankBookDto.getUserId().equals(userId))
                .collect(Collectors.toList());

        if (userBankBooks.isEmpty())
            throw new UserNotFoundException("Счета по id пользователя не найдены! В удалении отказано. ", userId);

        userBankBooks.forEach(bbd -> bankBookMap.remove(bbd.getId()));
    }
}
