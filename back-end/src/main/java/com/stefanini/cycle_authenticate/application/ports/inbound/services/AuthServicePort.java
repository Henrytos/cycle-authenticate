package com.stefanini.cycle_authenticate.application.ports.inbound.services;

import com.stefanini.cycle_authenticate.application.ports.outbound.dtos.SendMailRequestDTO;

public interface AuthServicePort {

    void sendRecoveryMail(String to);

}
