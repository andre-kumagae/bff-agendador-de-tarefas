package com.javanauta.bffagendadortarefas.business;


import com.javanauta.bffagendadortarefas.business.dto.in.TarefasDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bffagendadortarefas.infrastructure.client.TarefasClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {
    private final TarefasClient tarefasClient;

    public TarefasDTOResponse gravarTarefa(String token, TarefasDTORequest tarefasDTO) {
        return tarefasClient.gravarTarefas(tarefasDTO, token);
    }

    public List<TarefasDTOResponse> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token) {
        return tarefasClient.buscaListaDeTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefasDTOResponse> buscaTarefaPorEmail(String token) {
        return tarefasClient.buscaTarefasPorEmail(token);
    }

    public void deletaTarefaPorId(String id, String token) {
        tarefasClient.deletaTarefaPorId(id, token);
    }

    public TarefasDTOResponse alteraStatus(StatusNotificacaoEnum status, String id, String token) {
        return tarefasClient.alteraStatusNotificacao(status, id, token);
    }

    public TarefasDTOResponse atualizaTarefas(TarefasDTORequest tarefasDTO, String id, String token) {
        return tarefasClient.updateTarefas(tarefasDTO, id, token);
    }
}
