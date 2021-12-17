package ru.rastorguev.springhw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rastorguev.springhw.model.entity.CurrencyEntity;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {

    CurrencyEntity findByCurrency(String currency);
    boolean existsByCurrency(String currency);

}
