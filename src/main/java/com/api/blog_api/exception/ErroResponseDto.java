package com.api.blog_api.exception;

import java.time.Instant;

public record ErroResponseDto(
        Integer status, 
        String erro,
        String mensagem,        
        Instant dataHora
) {
}