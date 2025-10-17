package com.stefanini.cycle_authenticate.application.ports.outbound.repositories;

import com.stefanini.cycle_authenticate.domain.entities.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepositoryPort {

    List<Transaction> findAllByUserId(UUID userId);
    Transaction save(Transaction transaction);
    List<Transaction> findRecentBySenderId(UUID senderId);
    List<Transaction> getThreeLargest(UUID senderId);

    void removeById(UUID transactionId);

    Transaction findById(UUID transactionId);
    Transaction update(Transaction transaction);
}
