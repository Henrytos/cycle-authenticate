package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories;

import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.TransactionRepositoryPort;
import com.stefanini.cycle_authenticate.domain.entities.Transaction;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers.TransactionMapper;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.TransactionModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {
    private final JpaTransactionModelRepository jpaTransactionModelRepository;
    private final TransactionMapper transactionMapper;

    public TransactionRepositoryAdapter(JpaTransactionModelRepository jpaTransactionModelRepository, TransactionMapper transactionMapper) {
        this.jpaTransactionModelRepository = jpaTransactionModelRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<Transaction> findAllByUserId(UUID userId) {
        List<TransactionModel> transactionsModel = this.jpaTransactionModelRepository.findAllBySenderId(userId);
        return transactionsModel.stream().map(transactionMapper::toDomain).toList();
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionModel transactionModel = this.jpaTransactionModelRepository.save(this.transactionMapper.toInfra(transaction));
        return this.transactionMapper.toDomain(transactionModel);
    }

    @Override
    public List<Transaction> findRecentBySenderId(UUID senderId) {
        List<TransactionModel> transactionsModel = this.jpaTransactionModelRepository.findAllBySenderRecent(senderId);

        return transactionsModel.stream().map(transactionMapper::toDomain).toList();
    }

    @Override
    public List<Transaction> getThreeLargest(UUID senderId) {
        List<TransactionModel> transactionsModel = this.jpaTransactionModelRepository.findThreeLargest(senderId);

        return transactionsModel.stream().map(transactionMapper::toDomain).toList();
    }

    @Override
    public void removeById(UUID transactionId) {
        this.jpaTransactionModelRepository.deleteById(transactionId);
    }

    @Override
    public Transaction findById(UUID transactionId) {
        Optional<TransactionModel> transactionModel = this.jpaTransactionModelRepository.findById(transactionId);

        return transactionModel.map(this.transactionMapper::toDomain).orElse(null);
    }

    @Override
    public Transaction update(Transaction transaction) {
        TransactionModel transactionModel = this.jpaTransactionModelRepository.save(this.transactionMapper.toInfra(transaction));
        return this.transactionMapper.toDomain(transactionModel);
    }
}
