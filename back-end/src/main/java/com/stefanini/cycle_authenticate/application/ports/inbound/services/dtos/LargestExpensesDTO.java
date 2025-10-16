package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

public record LargestExpensesDTO(
        Double value,
        String title,
        Integer percentage
) {
}
