package ru.rastorguev.springhw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rastorguev.springhw.model.entity.BankBookEntity;

import java.util.List;

public interface BankBookRepository extends JpaRepository<BankBookEntity, Integer> {

    List<BankBookEntity> findAllByUserId(Integer userId);

}
