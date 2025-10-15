package com.stefanini.cycle_authenticate.domain.entities;

import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {

    private UUID id;
    private UUID senderId;
    private String title;
    private Double value;
    private TypeTransaction typeTransaction;
    private MethodPayment methodPayment;
    private LocalDate dateOfPayment;

    public Transaction() {
    }

    public Transaction(UUID senderId, String title, Double value, TypeTransaction typeTransaction, MethodPayment methodPayment, LocalDate dateOfPayment) {
        this.id = null;
        this.senderId = senderId;
        this.title = title;
        this.value = value;
        this.typeTransaction = typeTransaction;
        this.methodPayment = methodPayment;
        this.dateOfPayment = dateOfPayment;
    }

    public Transaction(UUID id, UUID senderId, String title, Double value, TypeTransaction typeTransaction, MethodPayment methodPayment, LocalDate dateOfPayment) {
        this.id = id;
        this.senderId = senderId;
        this.title = title;
        this.value = value;
        this.typeTransaction = typeTransaction;
        this.methodPayment = methodPayment;
        this.dateOfPayment = dateOfPayment;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public MethodPayment getMethodPayment() {
        return methodPayment;
    }

    public void setMethodPayment(MethodPayment methodPayment) {
        this.methodPayment = methodPayment;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", title='" + title + '\'' +
                ", value=" + value +
                ", typeTransaction=" + typeTransaction +
                ", methodPayment=" + methodPayment +
                ", dateOfPayment=" + dateOfPayment +
                '}';
    }
}
