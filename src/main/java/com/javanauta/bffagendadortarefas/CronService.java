package com.javanauta.bffagendadortarefas;

import com.javanauta.bffagendadortarefas.business.EmailService;
import com.javanauta.bffagendadortarefas.business.TarefaService;
import com.javanauta.bffagendadortarefas.business.UsuarioService;
import com.javanauta.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bffagendadortarefas.infrastructure.client.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {
    private final TarefaService tarefaService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;
    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {
        String token = login(converterParaRequesterDTO());
        log.info("Iniciada a busca de tarefas");
        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFuturaMaisCincoMinutos = LocalDateTime.now().plusHours(1);
        List<TarefasDTOResponse> listaTarefas = tarefaService.buscaTarefasAgendadasPorPeriodo(horaAtual, horaFuturaMaisCincoMinutos, token);
        log.info("Tarefas encontradas " + listaTarefas );
        listaTarefas.forEach(tarefa -> {
            emailService.enviarEmail(tarefa);
            log.info("E-mail enviado para o usuário " + tarefa.getEmailUsuario());
            tarefaService.alteraStatus(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), token);
        });
        log.info("Finalizada a busca e notificação de tarefas");
    }

    public String login(LoginRequestDTO loginRequestDTO) {
        return usuarioService.loginUsuario(loginRequestDTO);
    }

    public LoginRequestDTO converterParaRequesterDTO() {
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }

}
