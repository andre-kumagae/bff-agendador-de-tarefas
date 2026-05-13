package com.javanauta.bffagendadortarefas.controller;


import com.javanauta.bffagendadortarefas.business.dto.in.TarefasDTORequest;
import com.javanauta.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bffagendadortarefas.infrastructure.client.enums.StatusNotificacaoEnum;
import com.javanauta.bffagendadortarefas.infrastructure.client.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefa do usuario")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {
    private final com.javanauta.bffagendadortarefas.business.TarefaService tarefaService;

    @PostMapping
    @Operation(summary = "Salvar tarefas", description = "Cria uma nova tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa salvo com sucesso")
    @ApiResponse(responseCode = "400", description = "Tarefa ja cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> gravarTarefas(@RequestBody TarefasDTORequest tarefasDTO, @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.gravarTarefa(token, tarefasDTO));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Buscar dados de tarefas por período", description = "Tarefa encontrada")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não cadastrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaDeTarefasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal, @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Buscar tarefas de usuários por e-mail", description = "Tarefa encontrada")
    @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não cadastrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.buscaTarefaPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta Tarefas por id", description = "Deleta uma tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id, @RequestHeader(name = "Authorization", required = false) String token) {
            tarefaService.deletaTarefaPorId(id, token);
            return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera o status da tarefa", description = "Altera o status da tarefa")
    @ApiResponse(responseCode = "200", description = "Status da tarefa atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum statusNotificacaoEnum, @RequestParam("id") String id, @RequestHeader(name = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefaService.alteraStatus(statusNotificacaoEnum, id, token));
    }

    @PutMapping
    @Operation(summary = "Atualiza Tarefa de Usuário", description = "Atualizar Tarefa de Usuário")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> updateTarefas(@RequestBody TarefasDTORequest tarefasDTO, @RequestParam("id") String id, @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefaService.atualizaTarefas(tarefasDTO, id, token));
    }
}
