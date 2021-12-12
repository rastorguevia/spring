package ru.rastorguev.springhw.validation;

import ru.rastorguev.springhw.model.entity.CurrencyEntity;
import ru.rastorguev.springhw.repository.CurrencyRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;

class CurrencyValidator implements ConstraintValidator<Currency, String> {

    CurrencyRepository currencyRepository;

    public CurrencyValidator(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
        Set<String> currencyFromDB = currencyRepository.findAll()
                .stream()
                .map(CurrencyEntity::getCurrency)
                .collect(Collectors.toSet());

        return currencyFromDB.contains(currency);
    }

}
