package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models;

import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "transactions")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "transaction_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserModel sender;

    private String title;
    private Double value;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction")
    private TypeTransaction typeTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "method_payment")
    private MethodPayment methodPayment;

    @Column(name = "date_of_payment")
    private LocalDate dateOfPayment;
}
