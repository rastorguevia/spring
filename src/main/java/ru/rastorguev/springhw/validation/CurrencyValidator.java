package ru.rastorguev.springhw.validation;

import ru.rastorguev.springhw.repository.CurrencyRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class CurrencyValidator implements ConstraintValidator<Currency, String> {

    CurrencyRepository currencyRepository;

    public CurrencyValidator(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext constraintValidatorContext) {
        return currencyRepository.existsByCurrency(currency);
    }

}
