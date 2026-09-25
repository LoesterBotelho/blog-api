package com.api.blog_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDto(

        @NotBlank(message = "Autor é obrigatório")
        @Size(
                min = 3,
                max = 100,
                message = "Autor deve possuir entre 3 e 100 caracteres"
        )
        String autor,

        @NotBlank(message = "Título é obrigatório")
        @Size(
                min = 3,
                max = 100,
                message = "Título deve possuir entre 3 e 100 caracteres"
        )
        String titulo,

        @NotBlank(message = "Texto é obrigatório")
        @Size(
                min = 10,
                message = "Texto deve possuir no mínimo 10 caracteres"
        )
        String texto
) {
}