package com.api.blog_api.exception;

public record ErroCampoResponse(
        String campo,
        String mensagem
) {
}