package ru.rastorguev.springhw.service;

import org.springframework.stereotype.Component;
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
import java.util.stream.Stream;

@Component
public class BankBookServiceImpl implements BankBookService {

    private final Map<Integer, List<BankBookDto>> bankBookMap = new ConcurrentHashMap<>();
    private final AtomicInteger sequenceId = new AtomicInteger(1);

    @PostConstruct
    private void init() {
        bankBookMap.put(1, Arrays.asList(
                new BankBookDto(sequenceId.getAndIncrement(), 1, "12345", new BigDecimal(250000), "RUB"),
                new BankBookDto(sequenceId.getAndIncrement(), 1, "123456", new BigDecimal(30000), "EUR")));
    }

    @Override
    public List<BankBookDto> getAllBankBookById(Integer userId) {
        List<BankBookDto> list = bankBookMap.get(userId);

        if (list == null) throw new UserNotFoundException("Счета по id пользователя не найдены! ", userId);
        else return list;
    }

    @Override
    public BankBookDto getBankBookById(Integer bankBookId) {
        List<BankBookDto> list = new ArrayList();

        for (Map.Entry<Integer, List<BankBookDto>> entry : bankBookMap.entrySet()) {
            list.addAll(entry.getValue());
        }

        BankBookDto bbd = list.stream()
                .filter(bankBookDto -> bankBookDto.getId().equals(bankBookId))
                .findFirst()
                .orElse(null);

        if (bbd == null) throw new BankBookNotFoundException("Такой счет не найден! ", bankBookId);
        else return bbd;
    }

    @Override
    public BankBookDto createBankBook(BankBookDto bankBookDto) {

        List<BankBookDto> list = bankBookMap.get(bankBookDto.getUserId());
        if (list == null) bankBookMap.put(bankBookDto.getUserId(), Arrays.asList(bankBookDto));



    }
}
