package com.stefanini.cycle_authenticate.application.ports.outbound.dtos;

import com.stefanini.cycle_authenticate.domain.value_objects.Email;

public record SendMailRequestDTO(Email from, Email to, String subject, String body) {

}
