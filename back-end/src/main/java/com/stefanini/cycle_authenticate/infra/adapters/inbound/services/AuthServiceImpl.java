package com.stefanini.cycle_authenticate.infra.adapters.inbound.services;

import com.stefanini.cycle_authenticate.application.exceptions.UserNotFoundException;
import com.stefanini.cycle_authenticate.application.ports.inbound.services.AuthServicePort;
import com.stefanini.cycle_authenticate.application.ports.outbound.dtos.SendMailRequestDTO;
import com.stefanini.cycle_authenticate.application.ports.outbound.providers.MailProvider;
import com.stefanini.cycle_authenticate.application.ports.outbound.repositories.UserRepositoryPort;
import com.stefanini.cycle_authenticate.domain.entities.User;
import com.stefanini.cycle_authenticate.domain.value_objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServicePort {

    private final MailProvider mailProvider;

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public void sendRecoveryMail(String to) {

        User user = userRepositoryPort.findByEmail(new Email(to))
                .orElseThrow(UserNotFoundException::new);

        user.setToken(UUID.randomUUID().toString());

        user.setExpireAtToken(LocalDateTime.now().plusMinutes(20));

        this.userRepositoryPort.save(user);

        this.mailProvider.send(
                new SendMailRequestDTO(
                        new Email(),
                        new Email(to),
                        "Redefinir a senha",
                        """
                        <body style="margin:0; padding:20px; font-family:Arial, sans-serif; background:#f4f6f8;">
                            <table role="presentation" width="100%"
                                   style="max-width:500px; margin:auto; background:#fff; border-radius:8px; padding:20px; border:1px solid #ddd;">
                                <tr>
                                    <td style="text-align:center;">
                                        <h2 style="margin:0; color:#55B02E;">Recuperação de conta</h2>
                                        <p style="font-size:14px; color:#333;">Olá, %s</p>
                                        <p style="font-size:14px; color:#333;">
                                            Clique no botão abaixo para redefinir sua senha:
                                        </p>
                                        <a href="%s" target="_blank"
                                           style="display:inline-block; margin:15px 0; padding:12px 20px; background:#55B02E; 
                                                  color:#fff; text-decoration:none; border-radius:6px; font-weight:bold;">
                                            Redefinir senha
                                        </a>
                                        <p style="font-size:12px; color:#666; margin-top:20px;">
                                            Se não foi você, ignore este e-mail.
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </body>
                        """.formatted(
                                user.getUsername(),
                                "http://localhost?token=%s".formatted(user.getToken())
                        )
                )
        );


    }


}
