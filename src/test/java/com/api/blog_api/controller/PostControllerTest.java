package com.api.blog_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import com.api.blog_api.dto.request.PostRequest;
import com.api.blog_api.dto.response.PostResponse;
import com.api.blog_api.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    private UUID id;
    private PostRequest request;
    private PostResponse response;

    @BeforeEach
    void setUp() {

        id = UUID.randomUUID();

        request = new PostRequest(
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );

        response = new PostResponse(
                id,
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );
    }

    @Test
    void deveListarTodosOsPosts() throws Exception {

        when(postService.listarTodos())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].autor").value("Loester Botelho"))
                .andExpect(jsonPath("$[0].titulo")
                        .value("Aprendendo Spring Boot"));
    }

    @Test
    void deveObterPostPorId() throws Exception {

        when(postService.obterPorId(id))
                .thenReturn(response);

        mockMvc.perform(
                get("/posts/{id}", id)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.autor")
                        .value("Loester Botelho"))
                .andExpect(jsonPath("$.titulo")
                        .value("Aprendendo Spring Boot"))
                .andExpect(jsonPath("$.texto")
                        .value("Conteúdo sobre desenvolvimento de APIs REST."));
    }

    @Test
    void deveIncluirPost() throws Exception {

        when(postService.incluir(any(PostRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.autor")
                        .value("Loester Botelho"))
                .andExpect(jsonPath("$.titulo")
                        .value("Aprendendo Spring Boot"));
    }

    @Test
    void deveAtualizarPost() throws Exception {

        when(postService.atualizar(
                any(UUID.class),
                any(PostRequest.class)
        )).thenReturn(response);

        mockMvc.perform(
                put("/posts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.titulo")
                        .value("Aprendendo Spring Boot"));
    }

    @Test
    void deveDeletarPost() throws Exception {

        doNothing()
                .when(postService)
                .deletar(id);

        mockMvc.perform(
                delete("/posts/{id}", id)
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarBadRequestQuandoAutorEstiverVazio()
            throws Exception {

        PostRequest requestInvalido = new PostRequest(
                "",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        requestInvalido
                                )
                        )
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoTituloEstiverVazio()
            throws Exception {

        PostRequest requestInvalido = new PostRequest(
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        requestInvalido
                                )
                        )
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoDataForNula()
            throws Exception {

        PostRequest requestInvalido = new PostRequest(
                "Loester Botelho",
                null,
                "Aprendendo Spring Boot",
                "Conteúdo sobre desenvolvimento de APIs REST."
        );

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        requestInvalido
                                )
                        )
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoTextoEstiverVazio()
            throws Exception {

        PostRequest requestInvalido = new PostRequest(
                "Loester Botelho",
                LocalDate.of(2026, 9, 16),
                "Aprendendo Spring Boot",
                ""
        );

        mockMvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        requestInvalido
                                )
                        )
        )
                .andExpect(status().isBadRequest());
    }
}