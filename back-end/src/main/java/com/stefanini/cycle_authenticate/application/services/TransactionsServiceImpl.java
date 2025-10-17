package com.stefanini.cycle_authenticate.application.services;

import com.stefanini.cycle_authenticate.application.exceptions.UnauthorizedException;
import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.TransactionsServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateTransactionDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.GetMetricsUserDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.LargestExpensesDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.TransactionRepositoryPort;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.domain.entities.Transaction;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public List<Transaction> findRecentTransactionsBySenderId(UUID senderId) {
        this.userRepositoryPort.findById(senderId).orElseThrow(UserNotFoundException::new);
        return this.transactionRepositoryPort.findRecentBySenderId(senderId);
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

        BigDecimal bigDecimalSpent  = new BigDecimal(String.valueOf(spent));
        bigDecimalSpent = bigDecimalSpent.setScale(2, RoundingMode.DOWN);

        double deposit = transactions.stream().mapToDouble((t) -> {
            if (t.getTypeTransaction().equals(TypeTransaction.DEPOSIT)) {
                return t.getValue();
            }
            return 0.0;
        }).sum();

        BigDecimal bigDecimalDeposit = new BigDecimal(String.valueOf(deposit));
        bigDecimalDeposit = bigDecimalDeposit.setScale(2, RoundingMode.DOWN);

        double investment = transactions.stream().mapToDouble((t) -> {
            if (t.getTypeTransaction().equals(TypeTransaction.INVESTMENT)) {
                return t.getValue();
            }
            return 0.0;
        }).sum();
        BigDecimal bigDecimalInvestment = new BigDecimal(String.valueOf(investment));
        bigDecimalInvestment = bigDecimalInvestment.setScale(2, RoundingMode.DOWN);


        BigDecimal bigDecimalSale =  bigDecimalDeposit.subtract(bigDecimalInvestment).subtract(bigDecimalSpent);
        bigDecimalSale = bigDecimalSale.setScale(2, RoundingMode.DOWN);

        return new GetMetricsUserDTO(bigDecimalSpent.doubleValue(), bigDecimalDeposit.doubleValue(), bigDecimalInvestment.doubleValue(), bigDecimalSale.doubleValue());
    }

    @Override
    public List<LargestExpensesDTO> getThreeLargestExpenses(UUID senderId) {

        this.userRepositoryPort.findById(senderId).orElseThrow(UserNotFoundException::new);
        List<Transaction> transactions = this.transactionRepositoryPort.getThreeLargest(senderId);

        GetMetricsUserDTO getMetricsUserDTO = this.getMetricsBySenderId(senderId);

        Double deposit  = getMetricsUserDTO.deposit();

        return transactions.stream().map(t-> new LargestExpensesDTO(t.getValue(), t.getTitle(),  (int) (t.getValue() / deposit * 100))).toList();
    }

    @Override
    public void removeTransactionById(UUID transactionId, UUID userId) {

        this.userRepositoryPort.findById(userId).orElseThrow(UserNotFoundException::new);
        Transaction transaction = this.transactionRepositoryPort.findById(transactionId);

            if(!transaction.getSenderId().equals(userId)){
                throw new UnauthorizedException();
            }
        this.transactionRepositoryPort.removeById(transactionId);
    }

    @Override
    public Transaction update(UUID userId, Transaction transaction) {
        User userFind = this.userRepositoryPort.findById(userId).orElseThrow(UserNotFoundException::new);
        Transaction transactionFind = this.transactionRepositoryPort.findById(transaction.getId());

        if(!transactionFind.getSenderId().equals(userFind.getId())){
            throw new UnauthorizedException();
        }

        return this.transactionRepositoryPort.update(transaction);
    }
}
