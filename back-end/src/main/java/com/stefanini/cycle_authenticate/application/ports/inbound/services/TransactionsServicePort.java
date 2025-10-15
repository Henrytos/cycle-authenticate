package com.stefanini.cycle_authenticate.application.ports.inbound.services;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateTransactionDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.GetMetricsUserDTO;
import com.stefanini.cycle_authenticate.domain.entities.Transaction;

import java.util.List;
import java.util.UUID;


public interface TransactionsServicePort {

    void addTransaction(CreateTransactionDTO createTransactionDTO);
    List<Transaction> findAllTransactionsBySenderId(UUID senderId);
    GetMetricsUserDTO getMetricsBySenderId(UUID senderId);

}
