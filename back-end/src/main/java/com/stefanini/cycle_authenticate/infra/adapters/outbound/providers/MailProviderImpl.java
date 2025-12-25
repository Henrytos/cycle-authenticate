package com.stefanini.cycle_authenticate.infra.adapters.outbound.providers;

import com.stefanini.cycle_authenticate.application.ports.outbound.dtos.SendMailRequestDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.providers.MailProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailProviderImpl implements MailProvider {

    private final JavaMailSender javaMailSender;

    @Override
    public boolean send(SendMailRequestDTO request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(request.from().getValue());
        message.setTo(request.to().getValue());
        message.setSubject(request.subject());
        message.setText(request.body());

        try {
            javaMailSender.send(message);
            return true;
        } catch (MailException ex) {
            ex.printStackTrace();
            return false;
        }

    }
}
