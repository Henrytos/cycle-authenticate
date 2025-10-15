package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.TransactionRepositoryPort;
import com.stefanini.cycle_authenticate.domain.entities.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {
    private final JpaTransactionModelRepository jpaTransactionModelRepository;

    public TransactionRepositoryAdapter(JpaTransactionModelRepository jpaTransactionModelRepository) {
        this.jpaTransactionModelRepository = jpaTransactionModelRepository;
    }

    @Override
    public List<Transaction> findAllByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public Transaction save(Transaction transaction) {
        return null;
    }
}
