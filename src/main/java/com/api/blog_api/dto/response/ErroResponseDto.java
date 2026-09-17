package com.api.blog_api.dto.response;

import java.time.LocalDateTime;

public record ErroResponseDto(
        Integer status,
        String erro,
        String mensagem,
        LocalDateTime dataHora
) {
}