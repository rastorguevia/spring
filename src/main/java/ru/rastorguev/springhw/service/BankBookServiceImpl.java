package ru.rastorguev.springhw.service;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.rastorguev.springhw.model.entity.BankBookEntity;
import ru.rastorguev.springhw.model.entity.CurrencyEntity;
import ru.rastorguev.springhw.model.exception.BankBookException;
import ru.rastorguev.springhw.model.exception.BankBookNotFoundException;
import ru.rastorguev.springhw.model.exception.UserNotFoundException;
import ru.rastorguev.springhw.model.dto.BankBookDto;
import ru.rastorguev.springhw.repository.BankBookRepository;
import ru.rastorguev.springhw.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BankBookServiceImpl implements BankBookService {

    private final BankBookRepository bankBookRepository;
    private final CurrencyRepository currencyRepository;

    public BankBookServiceImpl(BankBookRepository bankBookRepository, CurrencyRepository currencyRepository) {
        this.bankBookRepository = bankBookRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<BankBookDto> getAllBankBookByUserId(Integer userId) {
        List<BankBookDto> list = bankBookRepository.findAllByUserId(userId)
                        .stream()
                        .map(this::mapToDto)
                        .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list)) throw new UserNotFoundException("Счета по id пользователя не найдены! ", userId);
        else return list;
    }

    @Override
    public BankBookDto getBankBookById(Integer bankBookId) {
        return bankBookRepository.findById(bankBookId)
                .map(this::mapToDto)
                .orElseThrow(() -> new BankBookNotFoundException("Такой счет не найден! ", bankBookId));
    }

    @Override
    public BankBookDto createBankBook(BankBookDto bankBookDto) {
        Integer currencyId = currencyRepository.findByCurrency(bankBookDto.getCurrency()).getId();

        boolean bankBookExist = bankBookRepository.findByUserIdAndNumberAndCurrency(
                bankBookDto.getUserId(), bankBookDto.getNumber(), currencyId)
                .isPresent();

        if (bankBookExist) throw new BankBookException(
                "Cчет с данной валютой уже имеется в хранилище: BankBookDto.id = " + bankBookDto.getId() + " ",
                bankBookDto.getId());

        return mapToDto(bankBookRepository.save(mapToEntity(bankBookDto)));
    }

    @Override
    public BankBookDto updateBankBook(BankBookDto bbd) {
        BankBookEntity bankBookEntity = bankBookRepository.getById(bbd.getId());
        if (bankBookEntity.getId() == null)
            throw new BankBookException("Такого счета не существует. В обновлении отказано. ", bbd.getId());

        if (!bankBookEntity.getNumber().equalsIgnoreCase(bbd.getNumber()))
            throw new BankBookException("Номер счета менять нельзя. В обновлении отказано. ", bbd.getId());

        return mapToDto(bankBookRepository.save(mapToEntity(bbd)));
    }

    @Override
    public void deleteBankBook(Integer bankBookId) {
        BankBookEntity bankBookEntity =  bankBookRepository.getById(bankBookId);
        if (bankBookEntity.getId() == null)
            throw new BankBookNotFoundException("Такого счета не существует. В удалении отказано. ", bankBookId);

        bankBookRepository.deleteById(bankBookId);
    }

    @Override
    public void deleteAllBankBookByUserId(Integer userId) {
        List<BankBookEntity> userBankBooks = bankBookRepository.findAllByUserId(userId);

        if (userBankBooks.isEmpty())
            throw new UserNotFoundException("Счета по id пользователя не найдены! В удалении отказано. ", userId);

        bankBookRepository.deleteAll(userBankBooks);
    }

    // можно переделать на полноценный маппер -> будет присутствовать в след. дз
    private BankBookDto mapToDto(BankBookEntity bbe) {
        return BankBookDto.builder()
                .id(bbe.getId())
                .userId(bbe.getUserId())
                .number(bbe.getNumber())
                .amount(bbe.getAmount())
                .currency(bbe.getCurrency())
                .build();
    }

    private BankBookEntity mapToEntity(BankBookDto bbd) {
        BankBookEntity bankBookEntity = new BankBookEntity();
        bankBookEntity.setId(bbd.getId());
        bankBookEntity.setUserId(bbd.getUserId());
        bankBookEntity.setNumber(bbd.getNumber());
        bankBookEntity.setAmount(bbd.getAmount());
        bankBookEntity.setCurrency(bbd.getCurrency());
        return bankBookEntity;
    }
}
