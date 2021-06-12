package com.randikalakmal.supplierservice.repository;

import com.randikalakmal.supplierservice.model.Stock;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {

    @NotNull
    @Override
    Optional<Stock> findById(@NotNull Integer integer);
}
