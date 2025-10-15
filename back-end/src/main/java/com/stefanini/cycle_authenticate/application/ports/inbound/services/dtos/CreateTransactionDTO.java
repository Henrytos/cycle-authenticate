package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTransactionDTO(
        UUID senderId,
        String title,
        Double value,
        TypeTransaction typeTransaction,
        MethodPayment methodPayment,
        LocalDate dateOfPayment

) {
}
