package com.api.blog_api.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(

        @NotBlank(message = "Autor é obrigatório")
        @Size(
                min = 3,
                max = 100,
                message = "Autor deve possuir entre 3 e 100 caracteres"
        )
        String autor,

        @NotNull(message = "Data é obrigatória")
        @PastOrPresent(
                message = "Data não pode ser futura"
        )
        LocalDate data,

        @NotBlank(message = "Texto é obrigatório")
        @Size(
                min = 5,
                max = 1000,
                message = "Texto deve possuir entre 5 e 1000 caracteres"
        )
        String texto

) {
}