package com.stefanini.cycle_authenticate.application.ports.outbound.providers;

import com.stefanini.cycle_authenticate.application.ports.outbound.dtos.SendMailRequestDTO;

public interface MailProvider {
    boolean send(SendMailRequestDTO request);
}
