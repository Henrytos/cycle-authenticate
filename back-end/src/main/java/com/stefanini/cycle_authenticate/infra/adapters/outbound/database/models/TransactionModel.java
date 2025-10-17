package com.stefanini.cycle_authenticate.infra.adapters.outbound.database.models;

import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "transactions")
@Data
@NoArgsConstructor
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

    public TransactionModel(UUID id, UserModel sender, String title, Double value, TypeTransaction typeTransaction, MethodPayment methodPayment, LocalDate dateOfPayment) {
        this.id = id;
        this.sender = sender;
        this.title = title;
        this.value = value;
        this.typeTransaction = typeTransaction;
        this.methodPayment = methodPayment;
        this.dateOfPayment = dateOfPayment;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", sender=" + sender +
                ", title='" + title + '\'' +
                ", value=" + value +
                ", typeTransaction=" + typeTransaction +
                ", methodPayment=" + methodPayment +
                ", dateOfPayment=" + dateOfPayment +
                '}';
    }
}
