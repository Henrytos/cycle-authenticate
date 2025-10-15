package com.stefanini.cycle_authenticate.application.ports.outbound.repositories;

import com.stefanini.cycle_authenticate.domain.entities.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepositoryPort {

    List<Transaction> findAllByUserId(UUID userId);
    Transaction save(Transaction transaction);

}
