package com.javanauta.bffagendadortarefas.business.dto.in;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTORequest {
    private String telefone;
    private String ddd;
}
