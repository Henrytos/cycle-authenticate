package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.mappers;

import com.stefanini.cycle_authenticate.domain.entities.Transaction;
import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.TransactionModel;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models.UserModel;
import com.stefanini.cycle_authenticate.infra.adapters.outbound.database.repositories.JpaUserModelRepository;
import jakarta.annotation.PostConstruct; // ESSENCIAL para evitar a exceção de ordem de inicialização
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class TransactionMapper implements MapperI<Transaction, TransactionModel> {

    private final JpaUserModelRepository jpaUserModelRepository;

    public TransactionMapper(ModelMapper modelMapper, JpaUserModelRepository jpaUserModelRepository) {
        this.jpaUserModelRepository = jpaUserModelRepository;
    }


    @Override
    public TransactionModel toInfra(Transaction transaction) {
        UserModel sender = this.jpaUserModelRepository.findById(transaction.getSenderId()).orElse(null);

        return new TransactionModel(
                transaction.getId(),
                sender,
                transaction.getTitle(),
                transaction.getValue(),
                transaction.getTypeTransaction(),
                transaction.getMethodPayment(),
                transaction.getDateOfPayment()
        );
    }

    @Override
    public Transaction toDomain(TransactionModel transactionModel) {
        UUID senderId = (transactionModel.getSender() != null)
                ? transactionModel.getSender().getId()
                : null;

        return new Transaction(
                transactionModel.getId(),
                senderId,
                transactionModel.getTitle(),
                transactionModel.getValue(),
                transactionModel.getTypeTransaction(),
                transactionModel.getMethodPayment(),
                transactionModel.getDateOfPayment()
        );
    }
}