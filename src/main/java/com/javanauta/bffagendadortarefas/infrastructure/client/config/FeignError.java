package com.javanauta.bffagendadortarefas.infrastructure.client.config;


import com.javanauta.bffagendadortarefas.infrastructure.exception.BusinessException;
import com.javanauta.bffagendadortarefas.infrastructure.exception.ConflictException;
import com.javanauta.bffagendadortarefas.infrastructure.exception.ResourceNotFoundException;
import com.javanauta.bffagendadortarefas.infrastructure.exception.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 409:
                return new ConflictException("Erro de atributo ja existente");
            case 403:
                return new ResourceNotFoundException("Erro de atributo não encontrado");
            case 401:
                return new UnauthorizedException("Erro de atributo não autorizado");
            default:
                return new BusinessException("Erro de servidor");
        }
    }
}
