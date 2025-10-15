package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

import com.stefanini.cycle_authenticate.domain.value_objects.MethodPayment;
import com.stefanini.cycle_authenticate.domain.value_objects.TypeTransaction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTransactionDTO(
        @NotBlank
        @Schema(examples = "academia")
        String title,
        @NotNull
        @Schema(examples = "149.90")
        Double value,
        @ValueOfEnum(enumClass = TypeTransaction.class, message = "deveria ser DEPOSIT | SPENT | INVESTMENT")
        @Schema(examples = {"DEPOSIT", " SPENT", "INVESTMENT"})
        String typeTransaction,
        @ValueOfEnum(enumClass = MethodPayment.class, message = "deveria ser PIX | CREDIT | TICKET")
        @Schema(examples = {"DEPOSIT", " SPENT", "INVESTMENT"})
        String methodPayment,
        @NotNull(message = "data não pode estar vazia ou nula")
        @Schema(examples = "2025-10-10")
        LocalDate dateOfPayment

) {
}
