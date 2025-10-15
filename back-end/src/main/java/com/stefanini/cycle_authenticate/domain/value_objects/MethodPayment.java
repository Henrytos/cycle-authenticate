package com.stefanini.cycle_authenticate.domain.value_objects;

public enum MethodPayment {
    PIX("Pix"),
    CREDIT("Credito"),
    TICKET("Boleto");

    String name;

    MethodPayment(String name) {
        this.name = name;
    }
}
