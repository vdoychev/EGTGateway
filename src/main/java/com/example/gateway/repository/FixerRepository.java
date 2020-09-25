package com.example.gateway.repository;

import com.example.gateway.model.FixerModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FixerRepository extends CrudRepository<FixerModel, Integer> {

    FixerModel findTopByCurrencyOrderByTimestampDesc(String currency);

    @Query(value = "select * from fixer where currency = :currency order by timestamp desc limit :number", nativeQuery = true)
    List<FixerModel> findLastNRecordsByCurrency(@Param("currency") String currency, @Param("number") int number);

}
