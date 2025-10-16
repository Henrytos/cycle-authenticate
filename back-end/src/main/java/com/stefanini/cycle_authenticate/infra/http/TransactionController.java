package com.stefanini.cycle_authenticate.infra.http;

import com.stefanini.cycle_authenticate.application.ports.inbound.services.TransactionsServicePort;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.CreateTransactionDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.GetMetricsUserDTO;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos.LargestExpensesDTO;
import com.stefanini.cycle_authenticate.domain.entities.Transaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Operation(description = "Rota Criação de nova transação do ususario", summary = "Criação de nova transação")
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

    @GetMapping("/metrics")
    @Operation(description = "Rota para obter metricas do ususario seja gasto,investimento,deposito e saldo", summary = "Obter metricas do usuario")
    public ResponseEntity<GetMetricsUserDTO> getMetrics(
            HttpServletRequest request
    ){
        UUID userId = UUID.fromString(request.getAttribute("userId").toString());
        GetMetricsUserDTO getMetricsUserDTO = this.transactionsServicePort.getMetricsBySenderId(userId);

        return ResponseEntity.ok().body(getMetricsUserDTO);
    }

    @GetMapping("/recents")
    @Operation(description = "Rota de listagem das transaçõee recentes (9) por meio da data da transação", summary = "Obter as transações recentes")
    public ResponseEntity<List<Transaction>> getRecents(
            HttpServletRequest request
    ){

        UUID userId = UUID.fromString(request.getAttribute("userId").toString());
        List<Transaction> transactions = this.transactionsServicePort.findRecentTransactionsBySenderId(userId);

        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/three_biggest_expenses")
    @Operation(description = "Rota de listagem dos 3 maiores gastos do usuario no mês", summary = "Obter 3 maiores gastos")
    public ResponseEntity<List<LargestExpensesDTO>> getThreeLargestExpenses(
            HttpServletRequest request
    ){

        UUID userId = UUID.fromString(request.getAttribute("userId").toString());
        List<LargestExpensesDTO> transactions= this.transactionsServicePort.getThreeLargestExpenses(userId);

        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping()
    @Operation(description = "Rota de listagem de todas transações", summary = "Listagem de transações")
    public ResponseEntity<List<Transaction>> findALl(
            HttpServletRequest request
    ){
        UUID userId = UUID.fromString(request.getAttribute("userId").toString());
        List<Transaction> transactions = this.transactionsServicePort.findAllTransactionsBySenderId(userId);

        return ResponseEntity.ok().body(transactions);
    }

}
