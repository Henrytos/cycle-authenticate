package com.stefanini.cycle_authenticate.application.services;

import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.TransactionsServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateTransactionDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.GetMetricsUserDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.TransactionRepositoryPort;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.domain.entities.Transaction;
import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionsServiceImpl implements TransactionsServicePort {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    public TransactionsServiceImpl(TransactionRepositoryPort transactionRepositoryPort, UserRepositoryPort userRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
    }


    @Override
    public Transaction addTransaction(UUID senderId,CreateTransactionDTO createTransactionDTO) {
        this.userRepositoryPort.findById(senderId).orElseThrow(UserNotFoundException::new);

        Transaction transaction = new Transaction(
                 senderId,  createTransactionDTO.title(), createTransactionDTO.value(),  TypeTransaction.valueOf(createTransactionDTO.typeTransaction()), MethodPayment.valueOf(createTransactionDTO.methodPayment()), createTransactionDTO.dateOfPayment()
        );

        transaction = this.transactionRepositoryPort.save(transaction);

        return transaction;
    }

    @Override
    public List<Transaction> findAllTransactionsBySenderId(UUID senderId) {
        this.userRepositoryPort.findById(senderId).orElseThrow(UserNotFoundException::new);

        return this.transactionRepositoryPort.findAllByUserId(senderId);
    }

    @Override
    public GetMetricsUserDTO getMetricsBySenderId(UUID senderId) {
        List<Transaction> transactions = this.findAllTransactionsBySenderId(senderId);

        double spent = transactions.stream().mapToDouble((t) -> {
            if (t.getTypeTransaction().equals(TypeTransaction.SPENT)) {
                return t.getValue();
            }
            return 0.0;
        }).sum();

        double deposit = transactions.stream().mapToDouble((t) -> {
            if (t.getTypeTransaction().equals(TypeTransaction.DEPOSIT)) {
                return t.getValue();
            }
            return 0.0;
        }).sum();

        double investment = transactions.stream().mapToDouble((t) -> {
            if (t.getTypeTransaction().equals(TypeTransaction.INVESTMENT)) {
                return t.getValue();
            }
            return 0.0;
        }).sum();

        double sale = deposit - (spent + investment);

        return new GetMetricsUserDTO(spent, deposit, investment, sale);
    }
}
