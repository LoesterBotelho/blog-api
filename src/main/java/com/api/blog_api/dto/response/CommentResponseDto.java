package com.api.blog_api.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record CommentResponseDto(
        UUID id,
        String autor,
        LocalDate data,
        String texto,
        UUID postId
) {
}