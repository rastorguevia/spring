package ru.rastorguev.springhw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rastorguev.springhw.model.entity.BankBookEntity;

import java.util.List;
import java.util.Optional;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Integer> {

    List<BankBookEntity> findAllByUserId(Integer userId);
    Optional<BankBookEntity> findByUserIdAndNumberAndCurrency(Integer userId, String number, Integer currency);

}
