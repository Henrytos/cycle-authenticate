package com.stefanini.cycle_authenticate.infra.http;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.TransactionsServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateTransactionDTO;
import com.stefanini.cycle_authenticate.domain.entities.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "transactions")
@RequestMapping("/transactions")
@RestController
public class TransactionController {

    private final TransactionsServicePort transactionsServicePort;

    public TransactionController(TransactionsServicePort transactionsServicePort) {
        this.transactionsServicePort = transactionsServicePort;
    }

    @PostMapping
    @Operation(description = "Criação de nova transação", summary = "Rota Criação de nova transação do ususario")
    public ResponseEntity<Transaction> create(
            HttpServletRequest request,
            @Valid @RequestBody CreateTransactionDTO createTransactionBodyDTO
            ){
        String userId = request.getAttribute("userId").toString();

        Transaction transaction = this.transactionsServicePort.addTransaction(
                UUID.fromString(userId),
                new CreateTransactionDTO(
                        createTransactionBodyDTO.title(),
                        createTransactionBodyDTO.value(),
                        createTransactionBodyDTO.typeTransaction(),
                        createTransactionBodyDTO.methodPayment(),
                        createTransactionBodyDTO.dateOfPayment()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

}
