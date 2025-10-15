package com.stefanini.cycle_authenticate.domain.value_objects;

public enum TypeTransaction {
    DEPOSIT("Deposito"),
    SPENT("Gasto"),
    INVESTMENT("Investimento");

    String name;

    TypeTransaction(String name) {
        this.name = name;
    }
}
