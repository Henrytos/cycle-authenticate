package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

public record GetMetricsUserDTO (
        Double spent,
        Double deposit,
        Double investment,
        Double sale
){

    @Override
    public String toString() {
        return "GetMetricsUserDTO{" +
                "spent=" + spent +
                ", deposit=" + deposit +
                ", investment=" + investment +
                ", sale=" + sale +
                '}';
    }
}
